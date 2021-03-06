package org.starcoin.sirius.wallet.core

enum class ClientEventType{
    FINISH_EON_CHANGE,
    INIT_WITHDRAWAL,
    CANCEL_WITHDRAWAL,
    NEW_OFFLINE_TRANSACTION,
    NOTHING,
    HUB_SIGN,
    OPEN_BALANCE_UPDATE_CHALLENGE,
    OPEN_BALANCE_UPDATE_CHALLENGE_PASS,
    OPEN_TRANSFER_DELIVERY_CHALLENGE,
    OPEN_TRANSFER_DELIVERY_CHALLENGE_PASS,
    EON_CHANGE_EXCEPTION,
    HUB_COMMIT_FAIL,
    DEPOSIT
}