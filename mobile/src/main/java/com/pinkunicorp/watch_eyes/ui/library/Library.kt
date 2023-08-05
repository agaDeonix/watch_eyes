package com.pinkunicorp.watch_eyes.ui.library

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.pinkunicorp.common.eyes.BlackEye
import com.pinkunicorp.common.eyes.CommonEye
import com.pinkunicorp.common.mode.BaseMode
import com.pinkunicorp.watch_eyes.R


data class EyePagerContent(
    val eye: CommonEye
)

/**
 * The UI affording the actions the user can take, along with a list of the events and the image
 * to be sent to the wearable devices.
 */
@OptIn(ExperimentalPagerApi::class)
@Composable
fun Library(
    onBackClick: () -> Unit,
    allEyes: List<CommonEye>,
    currentEye: CommonEye,
    onSelectNewEye: (newEye: CommonEye, pos: Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth().background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            title = { Text(text = "Choose") }, navigationIcon = {
                IconButton(onClick = { onBackClick() }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        )
        val items = allEyes.sortedByDescending { it == currentEye }.map { EyePagerContent(it) }
        val pagerState = rememberPagerState()

        HorizontalPager(
            count = items.size,
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { currentPage ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    modifier = Modifier.align(CenterHorizontally),
                    text = items[currentPage].eye.getName(),
                    style = MaterialTheme.typography.h4
                )
                Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    Spacer(modifier = Modifier.weight(1f))
                    Box(modifier = Modifier.size(150.dp)) {
                        items[currentPage].eye.drawPreview()
                    }
                    Spacer(modifier = Modifier.weight(1f))
                }
                Text(
                    text = stringResource(id = R.string.library_title_modes),
                    style = MaterialTheme.typography.h5
                )
                LazyColumn {
                    items[currentPage].eye.modes.forEach { mode ->
                        item {
                            ModeDescription(mode = mode)
                        }
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                if (items[currentPage].eye != currentEye) {
                    Button(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        onClick = { onSelectNewEye(items[currentPage].eye, allEyes.indexOf(items[currentPage].eye)) },
                    ) {
                        Text(
                            text = "Select"
                        )
                    }
                }
            }
        }
        if (allEyes.size > 1) {
            Box(modifier = Modifier.padding(bottom = 24.dp)) {
                DotsIndicator(
                    items.size,
                    selectedIndex = pagerState.currentPage,
                    selectedColor = Color.DarkGray,
                    unSelectedColor = Color.LightGray
                )
            }
        }
    }
}

@Composable
fun ModeDescription(modifier: Modifier = Modifier, mode: BaseMode) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = mode.name,
            style = MaterialTheme.typography.h5
        )
    }

}

@Composable
fun DotsIndicator(
    totalDots: Int,
    selectedIndex: Int,
    selectedColor: Color,
    unSelectedColor: Color,
) {
    LazyRow(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()

    ) {

        items(totalDots) { index ->
            if (index == selectedIndex) {
                Box(
                    modifier = Modifier
                        .size(5.dp)
                        .clip(CircleShape)
                        .background(selectedColor)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(5.dp)
                        .clip(CircleShape)
                        .background(unSelectedColor)
                )
            }

            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
            }
        }
    }
}

@Preview
@Composable
fun LibraryPreview() {
    Library({}, listOf(BlackEye()), BlackEye(), { newEye, pos ->  })
}
