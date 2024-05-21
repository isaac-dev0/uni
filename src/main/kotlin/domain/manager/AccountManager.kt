package domain.manager

import domain.model.Account

interface AccountManager {

    fun createAccount(account: Account)

    fun getAccount(username: String): Account?

    fun getAccounts(): List<Account?>

    fun updateAccount(account: Account)

    fun deleteAccount(account: Account)

}