package domain.model

enum class Job(private val title: String) {

    JUNIOR_DEVELOPER("Junior Developer"),
    MID_LEVEL_DEVELOPER("Mid-level Developer"),
    SENIOR_DEVELOPER("Senior Developer"),
    PROJECT_MANAGER("Project Manager");

    fun getTitle(): String {
        return title
    }

}