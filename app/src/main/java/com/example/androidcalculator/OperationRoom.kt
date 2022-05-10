package com.example.androidcalculator

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import net.objecthunter.exp4j.Expression
import java.util.*

@Entity(tableName = "operation")
data class OperationRoom(
    @PrimaryKey val uuid: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "expression") val expression: String,
    val result: Double,
    val timestamp: Long
)