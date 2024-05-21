package utility.validation.account

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import domain.model.Account

class AccountExistsValidator(

    private val collection: MongoCollection<Account>

) : AccountValidator {

    override fun validate(account: Account): Boolean {
        return collection.find(Filters.eq("username", account.username)).firstOrNull() != null
    }

}