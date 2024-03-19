package di

import domain.AppointmentDataSource
import domain.ArticleDataSource
import org.example.nativacare.Database

expect class AppModule {
    val appointmentDataSource:AppointmentDataSource
    val articleDataSource: ArticleDataSource
}