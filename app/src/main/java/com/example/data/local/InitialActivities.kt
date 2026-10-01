package com.example.data.local

import com.example.data.local.entity.ActivityEntity

object InitialActivities {
    val list: List<ActivityEntity> = listOf(
        // ================= STUDY (16) =================
        ActivityEntity(
            activityId = "study_flashcards_5",
            title = "Review 5 Flashcards",
            description = "Active recall on five tricky terms or formulas to lock in memory.",
            category = "STUDY",
            minimumDuration = 3, maximumDuration = 8, defaultDuration = 5,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "study,exam,memory,concept", moodTags = "NORMAL,TIRED,ENERGETIC"
        ),
        ActivityEntity(
            activityId = "study_formula_review",
            title = "Review 3 Core Formulas",
            description = "Write down 3 formulas from memory, check against your notes, and note edge cases.",
            category = "STUDY",
            minimumDuration = 5, maximumDuration = 10, defaultDuration = 8,
            difficulty = "MEDIUM", energyRequired = "MEDIUM", indoor = true,
            goalTags = "math,physics,exam,engineering,stem", moodTags = "NORMAL,ENERGETIC"
        ),
        ActivityEntity(
            activityId = "study_feynman_concept",
            title = "Feynman One Concept",
            description = "Explain a tough topic out loud in plain English as if explaining to a 10-year-old.",
            category = "STUDY",
            minimumDuration = 5, maximumDuration = 12, defaultDuration = 8,
            difficulty = "MEDIUM", energyRequired = "MEDIUM", indoor = true,
            goalTags = "study,understanding,exam,science", moodTags = "NORMAL,ENERGETIC"
        ),
        ActivityEntity(
            activityId = "study_one_mcq",
            title = "Solve 2 Practice MCQs",
            description = "Pick two multiple-choice questions, solve under test conditions, and review the explanations.",
            category = "STUDY",
            minimumDuration = 4, maximumDuration = 10, defaultDuration = 6,
            difficulty = "MEDIUM", energyRequired = "MEDIUM", indoor = true,
            goalTags = "test,exam,prep,quiz", moodTags = "NORMAL,ENERGETIC"
        ),
        ActivityEntity(
            activityId = "study_skim_notes",
            title = "Skim Today's Lecture Notes",
            description = "Scan key headings, bold definitions, and charts from your latest class to strengthen retention.",
            category = "STUDY",
            minimumDuration = 7, maximumDuration = 15, defaultDuration = 10,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "class,notes,review,college", moodTags = "TIRED,NORMAL,RELAXED"
        ),
        ActivityEntity(
            activityId = "study_vocab_words",
            title = "Learn 5 Vocabulary Words",
            description = "Pick 5 GRE/academic or language vocabulary words, look up roots and write one example sentence each.",
            category = "STUDY",
            minimumDuration = 5, maximumDuration = 10, defaultDuration = 8,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "language,gre,vocab,english", moodTags = "NORMAL,TIRED,RELAXED"
        ),
        ActivityEntity(
            activityId = "study_diagram_sketch",
            title = "Sketch a System Diagram",
            description = "Draw a quick flow diagram or anatomical sketch without looking at references.",
            category = "STUDY",
            minimumDuration = 8, maximumDuration = 15, defaultDuration = 10,
            difficulty = "MEDIUM", energyRequired = "MEDIUM", indoor = true,
            goalTags = "biology,cs,engineering,diagram", moodTags = "NORMAL,ENERGETIC"
        ),
        ActivityEntity(
            activityId = "study_code_debug_trace",
            title = "Trace One Algorithm Step",
            description = "Step through a sorting or tree traversal algorithm on paper with a 5-element array.",
            category = "STUDY",
            minimumDuration = 10, maximumDuration = 20, defaultDuration = 15,
            difficulty = "HARD", energyRequired = "HIGH", indoor = true,
            goalTags = "programming,algorithms,cs,exam", moodTags = "ENERGETIC"
        ),
        ActivityEntity(
            activityId = "study_past_paper_question",
            title = "Tackle One Past Exam Question",
            description = "Set a strict 15-minute timer and write out a structured response to an actual past paper question.",
            category = "STUDY",
            minimumDuration = 12, maximumDuration = 25, defaultDuration = 15,
            difficulty = "HARD", energyRequired = "HIGH", indoor = true,
            goalTags = "exam,finals,midterms,prep", moodTags = "ENERGETIC"
        ),
        ActivityEntity(
            activityId = "study_cheatsheet_box",
            title = "Fill 1 Box on Your Cheat Sheet",
            description = "Synthesize an entire sub-chapter or unit into one compact 3x3 index card or cheatsheet box.",
            category = "STUDY",
            minimumDuration = 10, maximumDuration = 20, defaultDuration = 12,
            difficulty = "MEDIUM", energyRequired = "MEDIUM", indoor = true,
            goalTags = "summary,exam,cheatsheet", moodTags = "NORMAL,ENERGETIC"
        ),
        ActivityEntity(
            activityId = "study_podcast_academic",
            title = "Listen to Academic Explainer Audio",
            description = "Put on a 15-minute scientific breakdown, crash course, or history audio snippet while resting eyes.",
            category = "STUDY",
            minimumDuration = 12, maximumDuration = 25, defaultDuration = 15,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "audio,passive,science,history", moodTags = "TIRED,RELAXED"
        ),
        ActivityEntity(
            activityId = "study_mind_map",
            title = "Create a Topic Mind Map",
            description = "Start with a central node of your current subject and branch out into 3 tiers of connected ideas.",
            category = "STUDY",
            minimumDuration = 10, maximumDuration = 25, defaultDuration = 18,
            difficulty = "MEDIUM", energyRequired = "MEDIUM", indoor = true,
            goalTags = "visual,mindmap,overview,synthesis", moodTags = "NORMAL,ENERGETIC"
        ),
        ActivityEntity(
            activityId = "study_read_paper_abstract",
            title = "Read 1 Research Paper Abstract",
            description = "Browse arXiv or Google Scholar for one trending paper in your field and read Abstract + Conclusion.",
            category = "STUDY",
            minimumDuration = 6, maximumDuration = 15, defaultDuration = 10,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "research,academia,reading,trends", moodTags = "NORMAL,RELAXED"
        ),
        ActivityEntity(
            activityId = "study_active_recall_dump",
            title = "Brain Dump Everything on a Topic",
            description = "Take a blank sheet of paper and write down every single fact, date, or equation you remember in 5 min.",
            category = "STUDY",
            minimumDuration = 5, maximumDuration = 10, defaultDuration = 5,
            difficulty = "EASY", energyRequired = "MEDIUM", indoor = true,
            goalTags = "braindump,memory,review", moodTags = "ENERGETIC,NORMAL"
        ),
        ActivityEntity(
            activityId = "study_deep_focus_pomodoro",
            title = "Focused Study Sprint",
            description = "Single-task on your toughest textbook chapter or assignment problem with zero tab switching.",
            category = "STUDY",
            minimumDuration = 20, maximumDuration = 35, defaultDuration = 25,
            difficulty = "HARD", energyRequired = "HIGH", indoor = true,
            goalTags = "focus,deepwork,exam,assignment", moodTags = "ENERGETIC"
        ),
        ActivityEntity(
            activityId = "study_review_wrong_answers",
            title = "Analyze 3 Missed Test Questions",
            description = "Review 3 questions you got wrong on your last test or quiz and categorize why (misread vs concept gap).",
            category = "STUDY",
            minimumDuration = 8, maximumDuration = 15, defaultDuration = 10,
            difficulty = "MEDIUM", energyRequired = "MEDIUM", indoor = true,
            goalTags = "errorlog,analysis,improvement", moodTags = "NORMAL,ENERGETIC"
        ),

        // ================= CAREER (16) =================
        ActivityEntity(
            activityId = "career_resume_bullet",
            title = "Improve One Resume Bullet",
            description = "Rewrite one project or job bullet using the X-Y-Z formula: Accomplished X, measured by Y, by doing Z.",
            category = "CAREER",
            minimumDuration = 6, maximumDuration = 15, defaultDuration = 10,
            difficulty = "MEDIUM", energyRequired = "MEDIUM", indoor = true,
            goalTags = "resume,cv,internship,jobs", moodTags = "NORMAL,ENERGETIC"
        ),
        ActivityEntity(
            activityId = "career_linkedin_headline",
            title = "Polish LinkedIn Headline",
            description = "Craft a crisp 1-line headline summarizing your domain, key tech stack, and what you build.",
            category = "CAREER",
            minimumDuration = 5, maximumDuration = 10, defaultDuration = 6,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "linkedin,branding,networking", moodTags = "NORMAL,RELAXED"
        ),
        ActivityEntity(
            activityId = "career_research_company",
            title = "Research One Target Company",
            description = "Read the 'About', engineering blog, or recent funding round of a company you'd love to work at.",
            category = "CAREER",
            minimumDuration = 8, maximumDuration = 18, defaultDuration = 12,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "interview,research,jobhunt", moodTags = "NORMAL,TIRED,RELAXED"
        ),
        ActivityEntity(
            activityId = "career_star_story",
            title = "Draft 1 Behavioral STAR Story",
            description = "Write out a Situation, Task, Action, Result story for 'Tell me about a conflict or tight deadline'.",
            category = "CAREER",
            minimumDuration = 10, maximumDuration = 20, defaultDuration = 15,
            difficulty = "MEDIUM", energyRequired = "MEDIUM", indoor = true,
            goalTags = "interview,behavioral,prep,hr", moodTags = "NORMAL,ENERGETIC"
        ),
        ActivityEntity(
            activityId = "career_leetcode_easy",
            title = "Solve or Review 1 Coding Problem",
            description = "Open LeetCode/HackerRank, read a two-pointer or hash map problem, and write pseudocode.",
            category = "CAREER",
            minimumDuration = 12, maximumDuration = 25, defaultDuration = 15,
            difficulty = "HARD", energyRequired = "HIGH", indoor = true,
            goalTags = "coding,software,leetcode,interview", moodTags = "ENERGETIC"
        ),
        ActivityEntity(
            activityId = "career_github_readme",
            title = "Audit a GitHub Project README",
            description = "Add a clean demo GIF link, installation steps, and architecture overview to your pinned repo.",
            category = "CAREER",
            minimumDuration = 10, maximumDuration = 20, defaultDuration = 15,
            difficulty = "MEDIUM", energyRequired = "MEDIUM", indoor = true,
            goalTags = "github,portfolio,coding,open_source", moodTags = "NORMAL,ENERGETIC"
        ),
        ActivityEntity(
            activityId = "career_internship_search",
            title = "Bookmark 3 Open Job Postings",
            description = "Search job boards or company career portals and save 3 roles that match your current skills.",
            category = "CAREER",
            minimumDuration = 8, maximumDuration = 15, defaultDuration = 10,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "internship,jobs,applications", moodTags = "NORMAL,TIRED"
        ),
        ActivityEntity(
            activityId = "career_draft_cold_outreach",
            title = "Draft a Concise Networking Note",
            description = "Write a polite 3-sentence note to an engineer or alumnus asking for 10 minutes of perspective.",
            category = "CAREER",
            minimumDuration = 6, maximumDuration = 12, defaultDuration = 8,
            difficulty = "MEDIUM", energyRequired = "MEDIUM", indoor = true,
            goalTags = "networking,email,outreach", moodTags = "NORMAL,ENERGETIC"
        ),
        ActivityEntity(
            activityId = "career_read_tech_blog",
            title = "Read 1 Engineering Blog Article",
            description = "Learn how Netflix, Uber, or Stripe solve scalability, reliability, or design challenges.",
            category = "CAREER",
            minimumDuration = 10, maximumDuration = 20, defaultDuration = 12,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "tech,engineering,architecture,reading", moodTags = "NORMAL,RELAXED,TIRED"
        ),
        ActivityEntity(
            activityId = "career_skills_gap_check",
            title = "Conduct a Quick Skills Audit",
            description = "Compare your current tech stack against requirements in 2 job descriptions and list 1 gap.",
            category = "CAREER",
            minimumDuration = 5, maximumDuration = 12, defaultDuration = 8,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "skills,planning,strategy", moodTags = "NORMAL,RELAXED"
        ),
        ActivityEntity(
            activityId = "career_portfolio_review",
            title = "Check Portfolio on Mobile Screen",
            description = "Open your portfolio site on mobile and note 2 UI or copy improvements needed.",
            category = "CAREER",
            minimumDuration = 4, maximumDuration = 10, defaultDuration = 6,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "portfolio,design,web", moodTags = "NORMAL,TIRED"
        ),
        ActivityEntity(
            activityId = "career_quick_application",
            title = "Submit 1 Tailored Application",
            description = "Attach your resume, paste your portfolio link, answer basic form fields, and hit submit.",
            category = "CAREER",
            minimumDuration = 15, maximumDuration = 30, defaultDuration = 20,
            difficulty = "MEDIUM", energyRequired = "HIGH", indoor = true,
            goalTags = "apply,jobhunt,internship", moodTags = "ENERGETIC"
        ),
        ActivityEntity(
            activityId = "career_elevator_pitch",
            title = "Practice 30-Second Elevator Pitch",
            description = "Speak out loud: Who you are, what you build, and what problem you're passionate about solving.",
            category = "CAREER",
            minimumDuration = 3, maximumDuration = 7, defaultDuration = 5,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "speaking,pitch,interview", moodTags = "NORMAL,ENERGETIC"
        ),
        ActivityEntity(
            activityId = "career_salary_market_check",
            title = "Check Salary & Level Benchmarks",
            description = "Look up Levels.fyi or Glassdoor to understand realistic title tiers and compensation bands.",
            category = "CAREER",
            minimumDuration = 5, maximumDuration = 10, defaultDuration = 7,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "compensation,negotiation,career", moodTags = "RELAXED,NORMAL"
        ),
        ActivityEntity(
            activityId = "career_system_design_primer",
            title = "Review 1 System Design Concept",
            description = "Read a quick summary of Consistent Hashing, Rate Limiting, or CDN caching strategies.",
            category = "CAREER",
            minimumDuration = 10, maximumDuration = 20, defaultDuration = 15,
            difficulty = "MEDIUM", energyRequired = "MEDIUM", indoor = true,
            goalTags = "systemdesign,backend,interview", moodTags = "NORMAL,ENERGETIC"
        ),
        ActivityEntity(
            activityId = "career_recommendation_request",
            title = "Endorse or Thank a Collaborator",
            description = "Leave a LinkedIn skill endorsement or send a genuine thank-you message to a past project partner.",
            category = "CAREER",
            minimumDuration = 4, maximumDuration = 8, defaultDuration = 5,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "gratitude,networking,colleagues", moodTags = "RELAXED,NORMAL"
        ),

        // ================= LIFE (16) =================
        ActivityEntity(
            activityId = "life_clean_desk",
            title = "Clear Your Workspace",
            description = "Put stray cups in the kitchen, trash papers, and straighten your keyboard for a clear head.",
            category = "LIFE",
            minimumDuration = 3, maximumDuration = 8, defaultDuration = 5,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "tidy,habits,focus,environment", moodTags = "TIRED,STRESSED,NORMAL"
        ),
        ActivityEntity(
            activityId = "life_organize_screenshots",
            title = "Delete 10 Old Screenshots",
            description = "Open your photo album and clear out temporary tickets, random memes, and duplicate receipts.",
            category = "LIFE",
            minimumDuration = 3, maximumDuration = 7, defaultDuration = 5,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "digitaldeclutter,phone,photos", moodTags = "TIRED,NORMAL,RELAXED"
        ),
        ActivityEntity(
            activityId = "life_reply_one_message",
            title = "Reply to One Important Message",
            description = "Knock out that text or email you've been delaying for days. It takes under 2 minutes.",
            category = "LIFE",
            minimumDuration = 3, maximumDuration = 6, defaultDuration = 4,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "communication,inbox,habits", moodTags = "STRESSED,NORMAL,ENERGETIC"
        ),
        ActivityEntity(
            activityId = "life_plan_tomorrow",
            title = "Write Tomorrow's Top 3 Priorities",
            description = "Jot down the 3 most crucial items for tomorrow so you wake up with total clarity.",
            category = "LIFE",
            minimumDuration = 5, maximumDuration = 10, defaultDuration = 6,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "planning,priorities,habits", moodTags = "NORMAL,RELAXED,TIRED"
        ),
        ActivityEntity(
            activityId = "life_clear_downloads",
            title = "Clean Out Your Downloads Folder",
            description = "Delete stale installers, temporary zip files, and duplicated PDFs from your computer or phone.",
            category = "LIFE",
            minimumDuration = 5, maximumDuration = 12, defaultDuration = 8,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "digitaldeclutter,storage,clean", moodTags = "TIRED,NORMAL"
        ),
        ActivityEntity(
            activityId = "life_prepare_bag",
            title = "Pack & Ready Your Bag",
            description = "Check chargers, keys, water bottle, ID badge, and notebooks so your next departure is frictionless.",
            category = "LIFE",
            minimumDuration = 4, maximumDuration = 10, defaultDuration = 6,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "organization,preparation,routine", moodTags = "NORMAL,TIRED"
        ),
        ActivityEntity(
            activityId = "life_wipe_phone_screen",
            title = "Sanitize & Wipe Phone Screen",
            description = "Wipe off screen smudges and keyboard dust with a microfiber cloth.",
            category = "LIFE",
            minimumDuration = 2, maximumDuration = 5, defaultDuration = 3,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "hygiene,clean,screen", moodTags = "TIRED,STRESSED,NORMAL"
        ),
        ActivityEntity(
            activityId = "life_check_bills",
            title = "Check Pending Subscriptions & Bills",
            description = "Review your card statement or subscription list and cancel one unused trial.",
            category = "LIFE",
            minimumDuration = 5, maximumDuration = 12, defaultDuration = 8,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "finance,money,budget", moodTags = "NORMAL,ENERGETIC"
        ),
        ActivityEntity(
            activityId = "life_water_plants",
            title = "Check & Water Plants",
            description = "Touch soil moisture, water thirsty house plants, and remove dry leaves.",
            category = "LIFE",
            minimumDuration = 3, maximumDuration = 8, defaultDuration = 5,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "plants,nature,mindful", moodTags = "TIRED,RELAXED"
        ),
        ActivityEntity(
            activityId = "life_take_out_trash",
            title = "Empty Bins & Recycling",
            description = "Collect trash bags, recycling, and drop them at the bins.",
            category = "LIFE",
            minimumDuration = 4, maximumDuration = 10, defaultDuration = 6,
            difficulty = "EASY", energyRequired = "LOW", indoor = false,
            goalTags = "chore,home,environment", moodTags = "NORMAL,TIRED"
        ),
        ActivityEntity(
            activityId = "life_inbox_zero_sweep",
            title = "5-Minute Email Purge",
            description = "Archive or mark read promotional newsletters without opening them.",
            category = "LIFE",
            minimumDuration = 4, maximumDuration = 10, defaultDuration = 5,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "email,declutter,inbox", moodTags = "TIRED,NORMAL"
        ),
        ActivityEntity(
            activityId = "life_sort_mail",
            title = "Sort Physical Mail & Paperwork",
            description = "Open envelopes, shred junk ads, and file away tax or medical receipts.",
            category = "LIFE",
            minimumDuration = 6, maximumDuration = 15, defaultDuration = 10,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "paperwork,declutter,home", moodTags = "NORMAL,RELAXED"
        ),
        ActivityEntity(
            activityId = "life_fridge_check",
            title = "Inspect Fridge & Expiration Dates",
            description = "Toss expired condiments and note 2 ingredients to cook for dinner.",
            category = "LIFE",
            minimumDuration = 4, maximumDuration = 10, defaultDuration = 6,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "kitchen,food,prep", moodTags = "TIRED,NORMAL"
        ),
        ActivityEntity(
            activityId = "life_quick_laundry_fold",
            title = "Fold 1 Basket of Clothes",
            description = "Fold your clean tees and pants while listening to a favorite tune.",
            category = "LIFE",
            minimumDuration = 8, maximumDuration = 18, defaultDuration = 12,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "laundry,chore,home", moodTags = "TIRED,RELAXED,NORMAL"
        ),
        ActivityEntity(
            activityId = "life_backup_device",
            title = "Trigger Cloud or Disk Backup",
            description = "Plug in your drive or verify your cloud photo and code repo sync is up to date.",
            category = "LIFE",
            minimumDuration = 3, maximumDuration = 8, defaultDuration = 5,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "tech,safety,backup", moodTags = "NORMAL,RELAXED"
        ),
        ActivityEntity(
            activityId = "life_weekly_calendar_preview",
            title = "Preview Next 3 Days of Events",
            description = "Glance at your calendar to spot upcoming conflicts, appointments, or deadlines.",
            category = "LIFE",
            minimumDuration = 4, maximumDuration = 8, defaultDuration = 5,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "calendar,schedule,habits", moodTags = "NORMAL,TIRED"
        ),

        // ================= HEALTH (16) =================
        ActivityEntity(
            activityId = "health_drink_water",
            title = "Drink a Large Glass of Water",
            description = "Hydrate with a tall 300ml glass of fresh water to restore focus and fight fatigue.",
            category = "HEALTH",
            minimumDuration = 2, maximumDuration = 5, defaultDuration = 3,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "hydration,habits,energy", moodTags = "TIRED,STRESSED,NORMAL,RELAXED"
        ),
        ActivityEntity(
            activityId = "health_eye_break",
            title = "20-20-20 Eye Rest",
            description = "Look at an object at least 20 feet away for 20 seconds, blink gently, and relax eye muscles.",
            category = "HEALTH",
            minimumDuration = 2, maximumDuration = 5, defaultDuration = 3,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "eyes,screen,relief", moodTags = "TIRED,STRESSED,NORMAL"
        ),
        ActivityEntity(
            activityId = "health_neck_stretch",
            title = "Neck & Shoulder Release",
            description = "Slowly roll your neck side to side, then roll shoulders back 10 times to release desk tension.",
            category = "HEALTH",
            minimumDuration = 3, maximumDuration = 8, defaultDuration = 5,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "stretch,posture,relief", moodTags = "TIRED,STRESSED,NORMAL"
        ),
        ActivityEntity(
            activityId = "health_box_breathing",
            title = "4x4 Box Breathing",
            description = "Inhale for 4s, hold for 4s, exhale for 4s, hold empty for 4s. Repeat 4 cycles to lower cortisol.",
            category = "HEALTH",
            minimumDuration = 3, maximumDuration = 7, defaultDuration = 4,
            difficulty = "EASY", energyRequired = "LOW", indoor = true, isRest = true,
            goalTags = "breathing,calm,stress,wellness", moodTags = "STRESSED,TIRED,NORMAL"
        ),
        ActivityEntity(
            activityId = "health_wrist_stretch",
            title = "Typing Wrist & Forearm Stretch",
            description = "Extend right arm palm up, gently pull fingers down with left hand. Repeat both sides.",
            category = "HEALTH",
            minimumDuration = 3, maximumDuration = 6, defaultDuration = 4,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "wrists,desk,ergonomics", moodTags = "TIRED,NORMAL"
        ),
        ActivityEntity(
            activityId = "health_brisk_walk",
            title = "Take a Short Brisk Walk",
            description = "Step away from your desk, walk down the hallway or outside for fresh air and circulation.",
            category = "HEALTH",
            minimumDuration = 8, maximumDuration = 20, defaultDuration = 12,
            difficulty = "EASY", energyRequired = "MEDIUM", indoor = false,
            goalTags = "walk,movement,cardio,freshair", moodTags = "TIRED,STRESSED,NORMAL,ENERGETIC"
        ),
        ActivityEntity(
            activityId = "health_bodyweight_squats",
            title = "Do 15 Clean Bodyweight Squats",
            description = "Drive blood flow into your legs and glutes to combat hours of sitting.",
            category = "HEALTH",
            minimumDuration = 3, maximumDuration = 8, defaultDuration = 5,
            difficulty = "MEDIUM", energyRequired = "HIGH", indoor = true,
            goalTags = "fitness,legs,energy,workout", moodTags = "ENERGETIC,NORMAL"
        ),
        ActivityEntity(
            activityId = "health_posture_reset",
            title = "Wall Angels Posture Reset",
            description = "Stand flat against a wall with heels, back, and head touching. Move arms up and down 10 times.",
            category = "HEALTH",
            minimumDuration = 3, maximumDuration = 6, defaultDuration = 4,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "posture,back,spine", moodTags = "TIRED,NORMAL"
        ),
        ActivityEntity(
            activityId = "health_sunlight_break",
            title = "Get 5 Minutes of Natural Light",
            description = "Step near an open window or balcony to reset your circadian rhythm with natural photons.",
            category = "HEALTH",
            minimumDuration = 4, maximumDuration = 10, defaultDuration = 6,
            difficulty = "EASY", energyRequired = "LOW", indoor = false,
            goalTags = "sunlight,circadian,mood", moodTags = "TIRED,RELAXED,NORMAL"
        ),
        ActivityEntity(
            activityId = "health_healthy_snack",
            title = "Eat a Nutrient-Dense Snack",
            description = "Grab a handful of almonds, an apple, or carrot sticks instead of processed sugar.",
            category = "HEALTH",
            minimumDuration = 4, maximumDuration = 10, defaultDuration = 6,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "nutrition,food,energy", moodTags = "TIRED,NORMAL"
        ),
        ActivityEntity(
            activityId = "health_pushups_set",
            title = "One Solid Set of Push-Ups",
            description = "Perform as many clean push-ups as you can comfortably handle with strict core stability.",
            category = "HEALTH",
            minimumDuration = 3, maximumDuration = 6, defaultDuration = 4,
            difficulty = "MEDIUM", energyRequired = "HIGH", indoor = true,
            goalTags = "fitness,strength,upperbody", moodTags = "ENERGETIC"
        ),
        ActivityEntity(
            activityId = "health_hip_flexor_stretch",
            title = "Couch / Hip Flexor Stretch",
            description = "Kneel on one knee, lean forward gently to open up tight hips from prolonged sitting.",
            category = "HEALTH",
            minimumDuration = 4, maximumDuration = 8, defaultDuration = 5,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "mobility,hips,stretch", moodTags = "TIRED,NORMAL"
        ),
        ActivityEntity(
            activityId = "health_face_wash",
            title = "Splash Cold Water on Face",
            description = "A rapid mammalian dive reflex trigger: cold water splashing resets alertness instantly.",
            category = "HEALTH",
            minimumDuration = 2, maximumDuration = 5, defaultDuration = 3,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "refresh,energy,alertness", moodTags = "TIRED,STRESSED"
        ),
        ActivityEntity(
            activityId = "health_standing_desk_shift",
            title = "Stand While Working for 15 Min",
            description = "Raise your desk or place your laptop on a elevated counter to work standing up.",
            category = "HEALTH",
            minimumDuration = 12, maximumDuration = 25, defaultDuration = 15,
            difficulty = "EASY", energyRequired = "MEDIUM", indoor = true,
            goalTags = "ergonomics,standing,habits", moodTags = "NORMAL,ENERGETIC"
        ),
        ActivityEntity(
            activityId = "health_foam_roll",
            title = "Foam Roll Upper Back",
            description = "Roll gently across your thoracic spine to undo rounded-shoulder slouching.",
            category = "HEALTH",
            minimumDuration = 5, maximumDuration = 12, defaultDuration = 8,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "recovery,back,mobility", moodTags = "TIRED,STRESSED"
        ),
        ActivityEntity(
            activityId = "health_calf_raises",
            title = "Do 25 Slow Calf Raises",
            description = "Stand tall and lift your heels to pump venous blood back up to your heart and brain.",
            category = "HEALTH",
            minimumDuration = 2, maximumDuration = 5, defaultDuration = 3,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "circulation,legs,quick", moodTags = "TIRED,NORMAL"
        ),

        // ================= RELAX & INTENTIONAL REST (16) =================
        ActivityEntity(
            activityId = "relax_intentional_nothing",
            title = "Do Nothing Intentionally",
            description = "Lie back, put your phone face down, and let your brain wander freely with zero obligation.",
            category = "RELAX",
            minimumDuration = 5, maximumDuration = 15, defaultDuration = 8,
            difficulty = "EASY", energyRequired = "LOW", indoor = true, isRest = true,
            goalTags = "rest,nothing,mindful,recharge", moodTags = "TIRED,STRESSED,RELAXED"
        ),
        ActivityEntity(
            activityId = "relax_listen_music",
            title = "Listen to 2 Songs with Eyes Closed",
            description = "Put on comfortable headphones, close your eyes, and notice instruments you usually miss.",
            category = "RELAX",
            minimumDuration = 6, maximumDuration = 12, defaultDuration = 8,
            difficulty = "EASY", energyRequired = "LOW", indoor = true, isRest = true,
            goalTags = "music,audio,calm", moodTags = "TIRED,STRESSED,RELAXED,NORMAL"
        ),
        ActivityEntity(
            activityId = "relax_sensory_grounding",
            title = "5-4-3-2-1 Sensory Grounding",
            description = "Notice 5 things you see, 4 you feel, 3 you hear, 2 you smell, 1 you taste to ease anxiety.",
            category = "RELAX",
            minimumDuration = 3, maximumDuration = 8, defaultDuration = 5,
            difficulty = "EASY", energyRequired = "LOW", indoor = true, isRest = true,
            goalTags = "anxiety,grounding,mindful,stress", moodTags = "STRESSED,TIRED"
        ),
        ActivityEntity(
            activityId = "relax_ambient_sound",
            title = "Relax to Rain or Lo-Fi Audio",
            description = "Turn on soundscape audio like gentle rain or waves and let your shoulders drop.",
            category = "RELAX",
            minimumDuration = 7, maximumDuration = 20, defaultDuration = 10,
            difficulty = "EASY", energyRequired = "LOW", indoor = true, isRest = true,
            goalTags = "ambient,sound,lofi,chill", moodTags = "TIRED,STRESSED,RELAXED"
        ),
        ActivityEntity(
            activityId = "relax_short_meditation",
            title = "5-Minute Breath Awareness",
            description = "Simply follow the sensation of air at the tip of your nostrils without trying to change it.",
            category = "RELAX",
            minimumDuration = 4, maximumDuration = 10, defaultDuration = 5,
            difficulty = "EASY", energyRequired = "LOW", indoor = true, isRest = true,
            goalTags = "meditation,mindful,zen", moodTags = "STRESSED,RELAXED,TIRED"
        ),
        ActivityEntity(
            activityId = "relax_sit_outside",
            title = "Sit Silently on a Bench",
            description = "Sit outside, feel the ambient breeze, and observe tree branches or clouds moving.",
            category = "RELAX",
            minimumDuration = 8, maximumDuration = 20, defaultDuration = 12,
            difficulty = "EASY", energyRequired = "LOW", indoor = false, isRest = true,
            goalTags = "nature,freshair,unplug", moodTags = "TIRED,STRESSED,RELAXED"
        ),
        ActivityEntity(
            activityId = "relax_progressive_muscle",
            title = "Progressive Muscle Relaxation",
            description = "Tense feet for 5s then release. Move up through calves, thighs, hands, and jaw.",
            category = "RELAX",
            minimumDuration = 6, maximumDuration = 15, defaultDuration = 8,
            difficulty = "EASY", energyRequired = "LOW", indoor = true, isRest = true,
            goalTags = "tension,relax,body", moodTags = "STRESSED,TIRED"
        ),
        ActivityEntity(
            activityId = "relax_make_warm_tea",
            title = "Mindfully Brew a Cup of Tea",
            description = "Focus on the sound of boiling water, steam rising, and the warm ceramic mug in your hands.",
            category = "RELAX",
            minimumDuration = 5, maximumDuration = 12, defaultDuration = 8,
            difficulty = "EASY", energyRequired = "LOW", indoor = true, isRest = true,
            goalTags = "tea,ritual,warmth,slow", moodTags = "TIRED,RELAXED,NORMAL"
        ),
        ActivityEntity(
            activityId = "relax_power_nap_prep",
            title = "Power Nap Micro-Rest (15 min)",
            description = "Set an alarm, recline in a dimly lit spot, and enter non-sleep deep rest (NSDR).",
            category = "RELAX",
            minimumDuration = 12, maximumDuration = 25, defaultDuration = 15,
            difficulty = "EASY", energyRequired = "LOW", indoor = true, isRest = true,
            goalTags = "nap,nsdr,sleep,recharge", moodTags = "TIRED"
        ),
        ActivityEntity(
            activityId = "relax_scented_candle",
            title = "Light a Candle or Incense",
            description = "Enjoy a calming aroma like sandalwood, lavender, or cedar while stepping away from screens.",
            category = "RELAX",
            minimumDuration = 5, maximumDuration = 15, defaultDuration = 10,
            difficulty = "EASY", energyRequired = "LOW", indoor = true, isRest = true,
            goalTags = "aroma,scent,calm", moodTags = "STRESSED,RELAXED"
        ),
        ActivityEntity(
            activityId = "relax_gaze_out_window",
            title = "Window Gazing & Daydreaming",
            description = "Look out over the skyline or yard and let your subconscious connect loose thoughts.",
            category = "RELAX",
            minimumDuration = 4, maximumDuration = 10, defaultDuration = 6,
            difficulty = "EASY", energyRequired = "LOW", indoor = true, isRest = true,
            goalTags = "daydream,creativity,unwind", moodTags = "TIRED,NORMAL,RELAXED"
        ),
        ActivityEntity(
            activityId = "relax_hand_massage",
            title = "Self Hand & Palm Massage",
            description = "Apply lotion and use your thumb to apply circular pressure to the fleshy base of each finger.",
            category = "RELAX",
            minimumDuration = 3, maximumDuration = 7, defaultDuration = 5,
            difficulty = "EASY", energyRequired = "LOW", indoor = true, isRest = true,
            goalTags = "massage,touch,care", moodTags = "TIRED,STRESSED"
        ),
        ActivityEntity(
            activityId = "relax_gratitude_pause",
            title = "Count 3 Quiet Blessings",
            description = "Silently name 3 small things that went smoothly today—warm coffee, a kind smile, clean air.",
            category = "RELAX",
            minimumDuration = 2, maximumDuration = 6, defaultDuration = 3,
            difficulty = "EASY", energyRequired = "LOW", indoor = true, isRest = true,
            goalTags = "gratitude,peace,perspective", moodTags = "NORMAL,RELAXED,STRESSED"
        ),
        ActivityEntity(
            activityId = "relax_childs_pose",
            title = "Rest in Child's Pose for 3 Min",
            description = "Kneel on the floor, stretch arms forward, rest forehead on the mat, and breathe into your lower back.",
            category = "RELAX",
            minimumDuration = 3, maximumDuration = 6, defaultDuration = 4,
            difficulty = "EASY", energyRequired = "LOW", indoor = true, isRest = true,
            goalTags = "yoga,rest,back", moodTags = "TIRED,STRESSED"
        ),
        ActivityEntity(
            activityId = "relax_warm_shower",
            title = "Take a Warm Quick Shower",
            description = "Step into warm water to melt muscle tightness and feel refreshed.",
            category = "RELAX",
            minimumDuration = 10, maximumDuration = 20, defaultDuration = 12,
            difficulty = "EASY", energyRequired = "LOW", indoor = true, isRest = true,
            goalTags = "shower,water,refresh", moodTags = "TIRED,STRESSED"
        ),
        ActivityEntity(
            activityId = "relax_no_phone_radius",
            title = "10-Minute Screen-Free Sanctuary",
            description = "Leave all digital screens in another room and sit with a physical book, sketchpad, or pet.",
            category = "RELAX",
            minimumDuration = 8, maximumDuration = 18, defaultDuration = 10,
            difficulty = "EASY", energyRequired = "LOW", indoor = true, isRest = true,
            goalTags = "detox,screens,calm", moodTags = "STRESSED,TIRED,RELAXED"
        ),

        // ================= FUN (12) =================
        ActivityEntity(
            activityId = "fun_mini_puzzle",
            title = "Solve 1 Wordle or Mini Crossword",
            description = "Engage your pattern-recognition brain with a quick word or logic puzzle.",
            category = "FUN",
            minimumDuration = 3, maximumDuration = 8, defaultDuration = 5,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "puzzle,wordle,game", moodTags = "NORMAL,RELAXED,TIRED"
        ),
        ActivityEntity(
            activityId = "fun_interesting_fact",
            title = "Read 1 Fascinating Wikipedia Fact",
            description = "Hit 'Random Article' on Wikipedia or read about how octopus brains think with their tentacles.",
            category = "FUN",
            minimumDuration = 4, maximumDuration = 10, defaultDuration = 6,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "trivia,curiosity,reading", moodTags = "NORMAL,TIRED,RELAXED"
        ),
        ActivityEntity(
            activityId = "fun_doodle_freely",
            title = "Doodle Without Any Goal",
            description = "Grab a pen and fill a small margin with geometric spirals, funny faces, or abstract contours.",
            category = "FUN",
            minimumDuration = 5, maximumDuration = 12, defaultDuration = 7,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "art,creative,doodle", moodTags = "RELAXED,TIRED,NORMAL"
        ),
        ActivityEntity(
            activityId = "fun_humor_break",
            title = "Watch 1 Wholesome Animal Video",
            description = "A 3-minute clip of golden retriever puppies or baby otters playing boosts oxytocin instantly.",
            category = "FUN",
            minimumDuration = 3, maximumDuration = 6, defaultDuration = 4,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "video,humor,wholesome", moodTags = "TIRED,STRESSED"
        ),
        ActivityEntity(
            activityId = "fun_speed_chess_puzzle",
            title = "Solve 3 Chess Tactics",
            description = "Find the pin, fork, or checkmate in two moves on Lichess or Chess.com tactics trainer.",
            category = "FUN",
            minimumDuration = 5, maximumDuration = 12, defaultDuration = 7,
            difficulty = "MEDIUM", energyRequired = "MEDIUM", indoor = true,
            goalTags = "chess,tactics,logic", moodTags = "ENERGETIC,NORMAL"
        ),
        ActivityEntity(
            activityId = "fun_creative_haiku",
            title = "Write a 3-Line Haiku",
            description = "5 syllables, 7 syllables, 5 syllables describing the exact weather or mood right now.",
            category = "FUN",
            minimumDuration = 3, maximumDuration = 7, defaultDuration = 4,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "writing,haiku,poetry", moodTags = "NORMAL,RELAXED"
        ),
        ActivityEntity(
            activityId = "fun_learn_one_riff",
            title = "Play 2 Chords on Guitar or Keys",
            description = "Pick up your instrument, strum 2 chords or practice a fingerpicking pattern for 8 minutes.",
            category = "FUN",
            minimumDuration = 6, maximumDuration = 15, defaultDuration = 10,
            difficulty = "MEDIUM", energyRequired = "MEDIUM", indoor = true,
            goalTags = "music,guitar,piano,hobby", moodTags = "NORMAL,RELAXED"
        ),
        ActivityEntity(
            activityId = "fun_origami_crane",
            title = "Fold 1 Quick Origami Shape",
            description = "Grab any square paper and fold a simple paper airplane, crane, or geometric bookmark.",
            category = "FUN",
            minimumDuration = 5, maximumDuration = 12, defaultDuration = 8,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "origami,crafts,hands", moodTags = "NORMAL,RELAXED"
        ),
        ActivityEntity(
            activityId = "fun_streetview_trip",
            title = "Explore 1 Random City on Street View",
            description = "Drop the Google Street View pegman into a quiet alley in Kyoto, Rome, or Reykjavik.",
            category = "FUN",
            minimumDuration = 5, maximumDuration = 12, defaultDuration = 8,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "travel,geography,curiosity", moodTags = "TIRED,RELAXED"
        ),
        ActivityEntity(
            activityId = "fun_riddle_solve",
            title = "Challenge Yourself to a Classic Riddle",
            description = "Read a clever riddle and try thinking outside the box before peeking at the answer.",
            category = "FUN",
            minimumDuration = 3, maximumDuration = 6, defaultDuration = 4,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "riddle,brain,fun", moodTags = "NORMAL,TIRED"
        ),
        ActivityEntity(
            activityId = "fun_micro_comic",
            title = "Read 2 Webcomic Strips",
            description = "Read a strip of XKCD, Calvin & Hobbes, or Sarah's Scribbles for a chuckle.",
            category = "FUN",
            minimumDuration = 3, maximumDuration = 6, defaultDuration = 4,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "comics,laugh,humor", moodTags = "TIRED,STRESSED,NORMAL"
        ),
        ActivityEntity(
            activityId = "fun_memory_palace",
            title = "Build a 5-Room Memory Palace",
            description = "Picture 5 distinct furniture spots in your bedroom and place 5 fun imaginary items on them.",
            category = "FUN",
            minimumDuration = 5, maximumDuration = 10, defaultDuration = 7,
            difficulty = "EASY", energyRequired = "MEDIUM", indoor = true,
            goalTags = "memory,palace,brain", moodTags = "NORMAL,ENERGETIC"
        ),

        // ================= PERSONAL DEVELOPMENT (12) =================
        ActivityEntity(
            activityId = "self_journal_prompt",
            title = "Answer 1 Thoughtful Journal Prompt",
            description = "Reflect on: 'What is one thing currently taking up my mental bandwidth that doesn't matter?'",
            category = "PERSONAL_DEV",
            minimumDuration = 5, maximumDuration = 15, defaultDuration = 8,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "journal,reflection,clarity", moodTags = "RELAXED,NORMAL,TIRED"
        ),
        ActivityEntity(
            activityId = "self_read_book_pages",
            title = "Read 4 Pages of a Good Book",
            description = "Pick up a physical novel, biography, or philosophy book and read uninterrupted for 10 minutes.",
            category = "PERSONAL_DEV",
            minimumDuration = 8, maximumDuration = 20, defaultDuration = 10,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "reading,books,wisdom", moodTags = "NORMAL,RELAXED,TIRED"
        ),
        ActivityEntity(
            activityId = "self_duolingo_lesson",
            title = "Complete 1 Language Lesson",
            description = "Do a quick 4-minute streak session in Spanish, Japanese, German, or your target tongue.",
            category = "PERSONAL_DEV",
            minimumDuration = 4, maximumDuration = 10, defaultDuration = 6,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "language,learning,habits", moodTags = "NORMAL,TIRED"
        ),
        ActivityEntity(
            activityId = "self_quote_reflection",
            title = "Meditate on a Stoic Quote",
            description = "Marcus Aurelius: 'You have power over your mind - not outside events. Realize this, and you will find strength.'",
            category = "PERSONAL_DEV",
            minimumDuration = 3, maximumDuration = 7, defaultDuration = 4,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "stoicism,philosophy,mindset", moodTags = "STRESSED,NORMAL,RELAXED"
        ),
        ActivityEntity(
            activityId = "self_weekly_habit_review",
            title = "Audit Your Habit Scorecard",
            description = "Review which habits you kept this week and adjust cues to make tomorrow smoother.",
            category = "PERSONAL_DEV",
            minimumDuration = 5, maximumDuration = 12, defaultDuration = 8,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "habits,review,growth", moodTags = "NORMAL,RELAXED"
        ),
        ActivityEntity(
            activityId = "self_typing_speed_test",
            title = "1-Minute Typing Test on MonkeyType",
            description = "Warm up fingers and aim for 98%+ accuracy on common English vocabulary.",
            category = "PERSONAL_DEV",
            minimumDuration = 3, maximumDuration = 6, defaultDuration = 4,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "skills,typing,speed", moodTags = "ENERGETIC,NORMAL"
        ),
        ActivityEntity(
            activityId = "self_curiosity_log",
            title = "Write 1 Question in Your Curiosity Log",
            description = "Jot down something you've always wondered about (e.g. how fiber optic cables cross the ocean).",
            category = "PERSONAL_DEV",
            minimumDuration = 3, maximumDuration = 7, defaultDuration = 4,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "curiosity,writing,wonder", moodTags = "RELAXED,NORMAL"
        ),
        ActivityEntity(
            activityId = "self_ted_talk_audio",
            title = "Listen to an Idea Worth Spreading",
            description = "Tune into a short 10-minute talk on creativity, resilience, or cognitive science.",
            category = "PERSONAL_DEV",
            minimumDuration = 10, maximumDuration = 20, defaultDuration = 12,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "ted,ideas,audio,learning", moodTags = "TIRED,NORMAL,RELAXED"
        ),
        ActivityEntity(
            activityId = "self_unsent_letter",
            title = "Write a Micro-Letter to Your Future Self",
            description = "Write 4 sentences on what you hope your future self in 6 months remembers about today.",
            category = "PERSONAL_DEV",
            minimumDuration = 5, maximumDuration = 12, defaultDuration = 7,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "reflection,future,writing", moodTags = "RELAXED,NORMAL"
        ),
        ActivityEntity(
            activityId = "self_budget_checkin",
            title = "Log Recent Small Expenses",
            description = "Note coffee or meal expenses from the last 2 days into your tracker or spreadsheet.",
            category = "PERSONAL_DEV",
            minimumDuration = 4, maximumDuration = 8, defaultDuration = 5,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "money,budget,finance", moodTags = "NORMAL,RELAXED"
        ),
        ActivityEntity(
            activityId = "self_voice_memo_diary",
            title = "Record a 2-Minute Audio Reflection",
            description = "Speak candidly into your voice recorder about a breakthrough, challenge, or realization.",
            category = "PERSONAL_DEV",
            minimumDuration = 3, maximumDuration = 6, defaultDuration = 4,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "audio,journal,expression", moodTags = "RELAXED,NORMAL"
        ),
        ActivityEntity(
            activityId = "self_goal_microstep",
            title = "Clarify Next Physical Action for 1 Goal",
            description = "Convert an abstract goal like 'get fit' into the next physical 5-minute action you can take.",
            category = "PERSONAL_DEV",
            minimumDuration = 4, maximumDuration = 8, defaultDuration = 5,
            difficulty = "EASY", energyRequired = "LOW", indoor = true,
            goalTags = "goals,planning,clarity", moodTags = "NORMAL,ENERGETIC"
        )
    )
}
