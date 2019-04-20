package com.example.yourhealth.models

class UserInfo(var generalStats: GeneralStats = GeneralStats(), var sleepStats: SleepStats = SleepStats(), var id: Int = 0,var name: String = "Default Name", var type: String = "pacient")


class Appointment(var date: String = "Default date", var description: String = "Default description")