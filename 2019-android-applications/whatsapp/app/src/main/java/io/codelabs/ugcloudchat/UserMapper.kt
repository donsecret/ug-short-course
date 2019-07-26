package io.codelabs.ugcloudchat

/**
 * Custom mapper class to map any class[IN] to [OUT]
 */
interface UserMapper<OUT, IN> {

    abstract fun map(input: IN): OUT
}