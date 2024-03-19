package di

import DatabaseDriverFactory
import data.local.SqlDelightAppointmentDataSource
import data.local.SqlDelightArticleDataSource
import domain.AppointmentDataSource
import domain.ArticleDataSource
import org.example.nativacare.Database


actual class AppModule {

    actual val appointmentDataSource: AppointmentDataSource by lazy {
        SqlDelightAppointmentDataSource(
            db = Database(
                driver = DatabaseDriverFactory().create()
            )
        )
    }

    actual val articleDataSource: ArticleDataSource by lazy {
        SqlDelightArticleDataSource(
            db = Database(
                driver = DatabaseDriverFactory().create()
            )
        )
    }
}