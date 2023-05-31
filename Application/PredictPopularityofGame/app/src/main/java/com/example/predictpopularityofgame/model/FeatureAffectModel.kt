package com.example.predictpopularityofgame.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class FeatureAffectModel(
    val estimated_sales: String,
    val estimated_sales_class: Int,
    val negative: ArrayList<String>,
    val positive: ArrayList<String>,
) : Parcelable