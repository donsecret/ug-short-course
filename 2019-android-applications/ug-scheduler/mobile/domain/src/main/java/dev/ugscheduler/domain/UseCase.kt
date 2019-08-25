package dev.ugscheduler.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.ugscheduler.domain.internal.DefaultScheduler
import dev.ugscheduler.domain.internal.Scheduler
import dev.ugscheduler.domain.result.Result
import timber.log.Timber

/**
 * Executes business login synchronously or asynchronously using a [Scheduler]
 */
abstract class UseCase<in P, R> {
    protected var scheduler: Scheduler = DefaultScheduler

    /**
     * Executes the use case asynchronously
     */
    operator fun invoke(params: P, result: MutableLiveData<Result<R>>) {
        try {
            scheduler.execute {
                try {
                    execute(params).let { response -> result.postValue(Result.Success(response)) }
                } catch (e: Exception) {
                    Timber.e(e)
                    result.postValue(Result.Error(e))
                }
            }
        } catch (e: Exception) {
            Timber.e(e)
            result.postValue(Result.Error(e))
        }
    }

    /** Executes the use case asynchronously and returns a [Result] in a new LiveData object.
     *
     * @return an observable [LiveData] with a [Result].
     *
     * @param params the input parameters to run the use case with
     */
    operator fun invoke(params: P): LiveData<Result<R>> {
        val callback: MutableLiveData<Result<R>> = MutableLiveData()
        this(params, callback)
        return callback
    }

    /**
     * Executes the use case synchronously
     */
    fun executeNow(params: P): Result<R> {
        return try {
            Result.Success(execute(params))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    /**
     * Override this to set the code to be executed
     */
    @Throws(RuntimeException::class)
    protected abstract fun execute(params: P): R
}

operator fun <R> UseCase<Unit, R>.invoke(): LiveData<Result<R>> = this(Unit)
operator fun <R> UseCase<Unit, R>.invoke(result: MutableLiveData<Result<R>>) = this(Unit, result)