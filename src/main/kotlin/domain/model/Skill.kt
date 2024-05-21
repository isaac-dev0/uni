package domain.model

data class Skill (

    val id: String,
    val name: String,
    val description: String?,
    val category: String?,
    val expiryDate: String?,
    val notes: String?,
    val proficiency: Proficiency?

)