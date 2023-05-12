package com.carefirstpraxis.carefirst_messenger_android.wearable.data

/**
 * Simple Data source for a list of fake watch models.
 */
class WatchLocalDataSource {
  val watches = listOf(
    WatchModel(
      messageId = 100001,
      subject = "Appt Cancellation",
      name = "Joe Jenkins",
      description = "I need to cancel my April 10th appointment.  Can we reschedule " +
        "it for some time the following week?"
    ),
    WatchModel(
      messageId = 100002,
      subject = "Test Results - Joe Jenkins",
      name = "Dr. James",
      description = "The test for Joe Jenkins are in.  Please consult the lab " +
        "for the latest results"
      ),
      WatchModel(
        messageId = 100003,
        subject = "Lab Work",
        name = "Mary Claret",
        description = "The lab work for Joseph Jenkins is now completed.  See attached " +
          "report."
      ),
      WatchModel(
        messageId = 100004,
        subject = "Appt Request",
        name = "Sarah Uline",
        description = "The portal suggested that I set a date for the follow-up " +
          "appointment.  I am on vacation but will be free in May."
      ),
      WatchModel(
        messageId = 100005,
        subject = "Follow Up",
        name = "Newton Lab",
        description = "The follow-up lab work is ready to be processed. " +
          "Check schedule for pickup time."
      ),
      WatchModel(
        messageId = 100006,
        subject = "Appt Cancellation",
        name = "Frank Pfinzer",
        description = "I need to cancel my follow-up appointment this week " +
          "but I can reschedule for next week."
      ),
      WatchModel(
        messageId = 100007,
        subject = "Prescription Refill",
        name = "Gene Vincent",
        description = "The Rx for Codeine needs a refill since I finished the " +
          "bottle last week."
      )
    )
}
