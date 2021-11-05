package com.example.basiccodelab2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.basiccodelab2.ui.theme.BasicCodelab2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicCodelab2Theme {
                // A surface container using the 'background' color from the theme
               MyApp()
            }
        }
    }
}
@Composable
fun Greetings(names: List<String>) {

    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)){
        items(items=names){
            Greeting(it)
        }
    }

}
@Composable
fun MyApp(){
    /*
    var shouldShowOnboarding by remember {
        //mutableStateof는 값이 변경되면 위 컴퍼저블 함수가 다시 실행됨
        mutableStateOf(true)
    }*/

    //rememberSaveable은 remember과 다르게 앱을 회전, 다크 모드 켜도 저장됨
    var shouldShowOnboarding by rememberSaveable {
        mutableStateOf(true)
    }

    if(shouldShowOnboarding){

        //함수를 전달함
        // 함수 안에 있는 내용은 즉시 실행되는 게 아니라 밑의 함수에서
        //실행 하면 실행됨
        OnBoardingScreen { shouldShowOnboarding = false }
    }else{

        Greetings(names = List<String>(10000){"$it"})
    }

}

@Composable
fun OnBoardingScreen(onContinueClicked: () -> Unit) {

    Surface {
        Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

            Text("Welcome to the Basics Codelab! ")
            Button(onClick = onContinueClicked,
            modifier = Modifier.padding(vertical = 24.dp)) {
                Text(text = "Continue")
            }
        }
    }

}

@Composable
fun Greeting(name: String) {
    val expanded = remember{ mutableStateOf(false)}
    val extraPadding by animateDpAsState(
        if(expanded.value) 48.dp else 0.dp,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioHighBouncy,
                stiffness = Spring.StiffnessLow
            )
    )

    Surface(color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extraPadding.coerceAtLeast(0.dp))
            ) {
                Text(text = "Hello,")
                Text(text = name)
                if(expanded.value){
                    Text(text="asdlfjkalsdfjl;dkjf asdlfjkdaf".repeat(4))
                }
            }
            IconButton(onClick = { expanded.value = !expanded.value }) {
                Icon(
                    imageVector = if (expanded.value) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if (expanded.value) {
                        stringResource(R.string.show_less)
                    } else {
                        stringResource(R.string.show_more)
                    }

                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BasicCodelab2Theme {
       MyApp()
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardPreview() {
    BasicCodelab2Theme {
        OnBoardingScreen {  }
    }
}