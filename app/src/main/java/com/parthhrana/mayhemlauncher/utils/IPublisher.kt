package com.parthhrana.mayhemlauncher.utils

interface IPublisher {
    fun attachSubscriber(s: ISubscriber)
    fun detachSubscriber(s: ISubscriber)
}
