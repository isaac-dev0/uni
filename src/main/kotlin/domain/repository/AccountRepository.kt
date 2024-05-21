package domain.repository

import com.mongodb.MongoException
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import domain.manager.AccountManager
import domain.model.Account
import org.bson.Document
import utility.Database
import utility.validation.account.AccountExistsValidator

class AccountRepository(

    private val collection: MongoCollection<Account>

) : AccountManager {

    companion object {
        private const val COLLECTION_NAME = "accounts"

        private val mongoClient: MongoClient = Database.getMongoClient()
        private val database: MongoDatabase = mongoClient.getDatabase(Database.DATABASE_NAME)
        val accountCollection: MongoCollection<Account> =
            database.getCollection(COLLECTION_NAME, Account::class.java)
    }

    override fun createAccount(account: Account) {
        if (AccountExistsValidator(accountCollection).validate(account)) {
            println("User with username: ${account.username} already exists.")
            return
        }
        try {
            val newAccount = collection.insertOne(account)
            println("Success! Inserted document with ID: ${newAccount.insertedId}")
        } catch (e: MongoException) {
            throw Exception(e)
        }
    }

    override fun getAccount(username: String): Account? {
        return collection.find(
            Filters.eq(
            "username", username
        )).firstOrNull()
    }

    override fun getAccounts(): List<Account?> {
        return collection.find().toList()
    }

    override fun updateAccount(account: Account) {
        val filter = Filters.eq("username", account.username)
        val newDocument = Document(
            "\$set", Document(
                mapOf(
                    "username" to account.username,
                    "password" to account.password,
                    "group" to account.group
                )
            )
        )
        collection.updateOne(filter, newDocument)
    }

    override fun deleteAccount(account: Account) {
        collection.deleteOneByUsername(account.username)
    }

    private fun MongoCollection<Account>.deleteOneByUsername(username: String) {
        this.findOneAndDelete(Document("username", username))
    }

}