package com.example.expense.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.expense.ui.theme.TextPrimary
import com.example.expense.ui.theme.Typography
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import com.example.expense.R
import com.example.expense.ui.theme.destroy


@Composable
fun TableRow(label:String,modifier:Modifier=Modifier,Arrow:Boolean=false,isdestroy:Boolean=false,content:(@Composable RowScope.()->Unit)?=null){
    val textcolor=if(isdestroy)destroy else TextPrimary
    Row(
            modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically

        ) {
            Text(label, style = Typography.bodyMedium, color = textcolor,modifier=Modifier.padding(vertical=10.dp))
            if (Arrow) {
                Icon(
                    painter = painterResource(id = R.drawable.chevron_right_fill0_wght400_grad0_opsz24),
                    contentDescription = null,
                    modifier=Modifier.padding(vertical=10.dp)
                )
            }
        if(content!=null){
            content()
        }
        }
    }
