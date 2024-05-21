package domain.model

enum class Group(val permissions: Set<Permission>) {
    EMPLOYEE(
        setOf(
            Permission.VIEW_PROFILE,
            Permission.EDIT_PROFILE
        )
    ),
    MANAGER(
        setOf(
            Permission.VIEW_PROFILE,
            Permission.EDIT_PROFILE,
            Permission.VIEW_STAFF,
            Permission.VIEW_SKILLS,
            Permission.EDIT_STAFF,
            Permission.EDIT_SKILLS
        )
    ),
    ADMINISTRATOR(
        setOf(
            Permission.VIEW_PROFILE,
            Permission.EDIT_PROFILE,
            Permission.VIEW_STAFF,
            Permission.VIEW_SKILLS,
            Permission.EDIT_STAFF,
            Permission.EDIT_SKILLS,
            Permission.ALL
        )
    )
}