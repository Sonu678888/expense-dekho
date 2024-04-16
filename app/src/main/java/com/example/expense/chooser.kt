package com.example.expense

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.expense.ui.theme.FillTertiary
import com.example.expense.ui.theme.Shapes
import com.example.expense.ui.theme.Typography

@Composable
fun chooser(
    label:String="",
    onClick:()->Unit,
    modifier:Modifier=Modifier
){
    Surface(
        shape= Shapes.medium,
        color= FillTertiary,
        modifier = modifier?:Modifier,
        onClick = onClick) {
        Row(modifier=Modifier.padding(horizontal = 20.dp,vertical=3.dp),
            verticalAlignment = Alignment.CenterVertically){
            Text(label,style= Typography.titleSmall)
            Icon(painter = painterResource(id = R.drawable.arrow_drop_down_circle_fill0_wght400_grad0_opsz24), contentDescription = null,
                modifier=Modifier.padding(start=10.dp)
                )



        }
    }

}
