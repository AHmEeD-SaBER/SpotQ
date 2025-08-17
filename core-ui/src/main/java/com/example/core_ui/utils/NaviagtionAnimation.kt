package com.example.core_ui.utils

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import com.example.core_ui.utils.Constants.ANIMATION_DURATION_MEDIUM

const val ANIM_DURATION = ANIMATION_DURATION_MEDIUM.toInt()

fun slideUpEnter(): EnterTransition =
    slideInVertically(
        animationSpec = tween(ANIM_DURATION),
        initialOffsetY = { fullHeight -> fullHeight }
    )

fun slideDownExit(): ExitTransition =
    slideOutVertically(
        animationSpec = tween(ANIM_DURATION),
        targetOffsetY = { fullHeight -> fullHeight }
    )

fun slideLeftEnter(): EnterTransition =
    slideInHorizontally(
        animationSpec = tween(ANIM_DURATION),
        initialOffsetX = { fullWidth -> fullWidth }
    )

fun slideRightExit(): ExitTransition =
    slideOutHorizontally(
        animationSpec = tween(ANIM_DURATION),
        targetOffsetX = { fullWidth -> fullWidth }
    )

fun fadeEnter(): EnterTransition = fadeIn(animationSpec = tween(ANIM_DURATION))
fun fadeExit(): ExitTransition = fadeOut(animationSpec = tween(ANIM_DURATION))

fun slideUpFadeEnter(): EnterTransition =
    slideUpEnter() + fadeEnter()

fun slideDownFadeExit(): ExitTransition =
    slideDownExit() + fadeExit()
