package utility.validation.account

import domain.model.Account

interface AccountValidator {

    fun validate(account: Account): Boolean

}