package com.example.engine

import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.provider.CalendarContract
import androidx.core.content.ContextCompat
import com.example.data.model.CalendarGap

object CalendarGapEngine {

    fun hasCalendarPermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.READ_CALENDAR
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun findUpcomingGaps(context: Context): List<CalendarGap> {
        if (!hasCalendarPermission(context)) {
            return emptyList()
        }

        val gaps = mutableListOf<CalendarGap>()
        val now = System.currentTimeMillis()
        val endWindow = now + (8 * 60 * 60 * 1000) // next 8 hours

        data class EventItem(val title: String, val start: Long, val end: Long)

        val events = mutableListOf<EventItem>()

        val projection = arrayOf(
            CalendarContract.Events.TITLE,
            CalendarContract.Events.DTSTART,
            CalendarContract.Events.DTEND
        )

        val selection = "${CalendarContract.Events.DTSTART} >= ? AND ${CalendarContract.Events.DTSTART} <= ? AND ${CalendarContract.Events.DELETED} = 0"
        val selectionArgs = arrayOf(now.toString(), endWindow.toString())
        val sortOrder = "${CalendarContract.Events.DTSTART} ASC"

        try {
            val cursor: Cursor? = context.contentResolver.query(
                CalendarContract.Events.CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                sortOrder
            )

            cursor?.use {
                val titleIndex = it.getColumnIndex(CalendarContract.Events.TITLE)
                val startIndex = it.getColumnIndex(CalendarContract.Events.DTSTART)
                val endIndex = it.getColumnIndex(CalendarContract.Events.DTEND)

                while (it.moveToNext()) {
                    val title = it.getString(titleIndex) ?: "Scheduled Event"
                    val start = it.getLong(startIndex)
                    val end = it.getLong(endIndex)
                    if (end > start) {
                        events.add(EventItem(title, start, end))
                    }
                }
            }
        } catch (e: SecurityException) {
            return emptyList()
        } catch (e: Exception) {
            return emptyList()
        }

        // Detect gaps between events
        for (i in 0 until events.size - 1) {
            val current = events[i]
            val next = events[i + 1]
            val gapMillis = next.start - current.end
            val gapMinutes = (gapMillis / (60 * 1000)).toInt()

            if (gapMinutes in 5..60) {
                gaps.add(
                    CalendarGap(
                        startMillis = current.end,
                        endMillis = next.start,
                        durationMinutes = gapMinutes,
                        titleBefore = current.title,
                        titleAfter = next.title
                    )
                )
            }
        }

        // If no events found or only 1 event, optionally provide a demo gap if calendar is connected but empty
        if (gaps.isEmpty() && events.isNotEmpty()) {
            val first = events.first()
            val minutesUntil = ((first.start - now) / (60 * 1000)).toInt()
            if (minutesUntil in 5..60) {
                gaps.add(
                    CalendarGap(
                        startMillis = now,
                        endMillis = first.start,
                        durationMinutes = minutesUntil,
                        titleBefore = "Now",
                        titleAfter = first.title
                    )
                )
            }
        }

        return gaps
    }
}
