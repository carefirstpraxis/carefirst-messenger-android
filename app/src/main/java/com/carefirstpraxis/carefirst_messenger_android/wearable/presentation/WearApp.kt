package com.carefirstpraxis.carefirst_messenger_android.wearable.presentation

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.activity
import androidx.navigation.navArgument
import androidx.wear.compose.foundation.lazy.ScalingLazyListState
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Vignette
import androidx.wear.compose.material.VignettePosition
import androidx.wear.compose.material.scrollAway
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.currentBackStackEntryAsState
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.carefirstpraxis.carefirst_messenger_android.wearable.R
import com.carefirstpraxis.carefirst_messenger_android.wearable.presentation.components.CustomTimeText
import com.carefirstpraxis.carefirst_messenger_android.wearable.presentation.navigation.DestinationScrollType
import com.carefirstpraxis.carefirst_messenger_android.wearable.presentation.navigation.SCROLL_TYPE_NAV_ARGUMENT
import com.carefirstpraxis.carefirst_messenger_android.wearable.presentation.navigation.Screen
import com.carefirstpraxis.carefirst_messenger_android.wearable.presentation.navigation.WATCH_ID_NAV_ARGUMENT
import com.carefirstpraxis.carefirst_messenger_android.wearable.presentation.theme.WearAppTheme
import com.carefirstpraxis.carefirst_messenger_android.wearable.presentation.theme.initialThemeValues
import com.carefirstpraxis.carefirst_messenger_android.wearable.presentation.theme.themeValues
import com.carefirstpraxis.carefirst_messenger_android.wearable.presentation.ui.ScalingLazyListStateViewModel
import com.carefirstpraxis.carefirst_messenger_android.wearable.presentation.ui.ScrollStateViewModel
import com.carefirstpraxis.carefirst_messenger_android.wearable.presentation.ui.dialog.Dialogs
import com.carefirstpraxis.carefirst_messenger_android.wearable.presentation.ui.landing.LandingScreen
import com.carefirstpraxis.carefirst_messenger_android.wearable.presentation.ui.map.MapActivity
import com.carefirstpraxis.carefirst_messenger_android.wearable.presentation.ui.progressindicator.FullScreenProgressIndicator
import com.carefirstpraxis.carefirst_messenger_android.wearable.presentation.ui.progressindicator.IndeterminateProgressIndicator
import com.carefirstpraxis.carefirst_messenger_android.wearable.presentation.ui.progressindicator.ProgressIndicatorsScreen
import com.carefirstpraxis.carefirst_messenger_android.wearable.presentation.ui.theme.ThemeScreen
import com.carefirstpraxis.carefirst_messenger_android.wearable.presentation.ui.userinput.SliderScreen
import com.carefirstpraxis.carefirst_messenger_android.wearable.presentation.ui.userinput.StepperScreen
import com.carefirstpraxis.carefirst_messenger_android.wearable.presentation.ui.userinput.UserInputComponentsScreen
import com.carefirstpraxis.carefirst_messenger_android.wearable.presentation.ui.watch.MessageDetailScreen
import com.carefirstpraxis.carefirst_messenger_android.wearable.presentation.ui.watch.MessageDetailViewModel
import com.carefirstpraxis.carefirst_messenger_android.wearable.presentation.ui.watchlist.MessageListScreen
import com.carefirstpraxis.carefirst_messenger_android.wearable.presentation.ui.watchlist.MessageListViewModel
import com.google.android.horologist.composables.DatePicker
import com.google.android.horologist.composables.TimePicker
import com.google.android.horologist.composables.TimePickerWith12HourClock
import java.time.LocalDateTime

@Composable
fun WearApp(
  modifier: Modifier = Modifier,
  swipeDismissableNavController: NavHostController = rememberSwipeDismissableNavController()
) {
    var themeColors by remember { mutableStateOf(initialThemeValues.colors) }
    WearAppTheme(colors = themeColors) {
      // Allows user to disable the text before the time.
      var showProceedingTextBeforeTime by rememberSaveable { mutableStateOf(false) }

      // Allows user to show/hide the vignette on appropriate screens.
      // IMPORTANT NOTE: Usually you want to show the vignette all the time on screens with
      // scrolling content, a rolling side button, or a rotating bezel. This preference is just
      // to visually demonstrate the vignette for the developer to see it on and off.
      var vignetteVisiblePreference by rememberSaveable { mutableStateOf(true) }

      // Observes the current back stack entry to pull information and determine if the screen
      // is scrollable and the scrollable state.
      //
      // The main reason the state for any scrollable screen is hoisted to this level is so the
      // Scaffold can properly place the position indicator (also known as the scroll indicator).
      //
      // We save the above scrollable states in the SavedStateHandle and retrieve them
      // when needed from custom view models (see ScrollingViewModels class).
      //
      // Screens with scrollable content:
      //  1. The watch list screen uses ScalingLazyColumn (backed by ScalingLazyListState)
      //  2. The watch detail screens uses Column with scrolling enabled (backed by ScrollState).
      //
      // We also use these scrolling states for various other things (like hiding the time
      // when the user is scrolling and only showing the vignette when the screen is
      // scrollable).
      //
      // Remember, mobile guidelines specify that if you back navigate out of a screen and then
      // later navigate into it again, it should be in its initial scroll state (not the last
      // scroll location it was in before you backed out).
      val currentBackStackEntry by swipeDismissableNavController.currentBackStackEntryAsState()

      val scrollType =
        currentBackStackEntry?.arguments?.getSerializable(SCROLL_TYPE_NAV_ARGUMENT)
          ?: DestinationScrollType.NONE

      // TODO: consider moving to ViewModel
      // Display value is passed down to various user input screens, for the slider and stepper
      // components specifically, to demonstrate how they work.
      var displayValueForUserInput by remember { mutableStateOf(5) }
      var dateTimeForUserInput by remember { mutableStateOf(LocalDateTime.now()) }

      Scaffold(
        modifier = modifier,
        timeText = {
          // Scaffold places time at top of screen to follow Material Design guidelines.
          // (Time is hidden while scrolling.)

          val timeTextModifier =
            when (scrollType) {
              DestinationScrollType.SCALING_LAZY_COLUMN_SCROLLING -> {
                val scrollViewModel: ScalingLazyListStateViewModel = viewModel(currentBackStackEntry!!)
                Modifier.scrollAway(scrollViewModel.scrollState)
              }

              DestinationScrollType.COLUMN_SCROLLING -> {
                val viewModel: ScrollStateViewModel = viewModel(currentBackStackEntry!!)
                Modifier.scrollAway(viewModel.scrollState)
              }

              DestinationScrollType.TIME_TEXT_ONLY -> {
                Modifier
              }
              else -> {
                null
              }
            }

            key(currentBackStackEntry?.destination?.route) {
              CustomTimeText(
                modifier = timeTextModifier ?: Modifier,
                visible = timeTextModifier != null,
                startText =
                if (showProceedingTextBeforeTime) {
                  stringResource(R.string.leading_time_text)
                }
                else {
                  null
                }
              )}
            },
            vignette = {
              // Only show vignette for screens with scrollable content.
              if (scrollType == DestinationScrollType.SCALING_LAZY_COLUMN_SCROLLING ||
                scrollType == DestinationScrollType.COLUMN_SCROLLING
              ) {
                if (vignetteVisiblePreference) {
                  Vignette(vignettePosition = VignettePosition.TopAndBottom)
                }
              }
            },
            positionIndicator = {
              // Only displays the position indicator for scrollable content.
              when (scrollType) {
                DestinationScrollType.SCALING_LAZY_COLUMN_SCROLLING -> {
                  // Get or create the ViewModel associated with the current back stack entry
                  val scrollViewModel: ScalingLazyListStateViewModel = viewModel(currentBackStackEntry!!)
                  PositionIndicator(scalingLazyListState = scrollViewModel.scrollState)
                }
                DestinationScrollType.COLUMN_SCROLLING -> {
                  // Get or create the ViewModel associated with the current back stack entry
                  val viewModel: ScrollStateViewModel = viewModel(currentBackStackEntry!!)
                  PositionIndicator(scrollState = viewModel.scrollState)
                }
              }
            }
        ) {
          /*
           * Wear OS's version of NavHost supports swipe-to-dismiss (similar to back
           * gesture on mobile). Otherwise, the code looks very similar.
           */
          SwipeDismissableNavHost(
            navController = swipeDismissableNavController,
            startDestination = Screen.Landing.route,
            modifier = Modifier.background(MaterialTheme.colors.background)
          ) {
            // Main Window
            composable(
              route = Screen.Landing.route,
              arguments = listOf(
                // In this case, the argument isn't part of the route, it's just attached
                // as information for the destination.
                navArgument(SCROLL_TYPE_NAV_ARGUMENT) {
                  type = NavType.EnumType(DestinationScrollType::class.java)
                  defaultValue = DestinationScrollType.SCALING_LAZY_COLUMN_SCROLLING
                })) {
                  val scalingLazyListState = scalingLazyListState(it)
                  val focusRequester = remember { FocusRequester() }
                  val menuItems = listOf(
                    menuNameAndCallback(
                      navController = swipeDismissableNavController,
                      menuNameResource = R.string.user_input_components_label,
                      screen = Screen.UserInputComponents
                    ),
                    menuNameAndCallback(
                      navController = swipeDismissableNavController,
                      menuNameResource = R.string.map_label,
                      screen = Screen.Map
                    ),
                    menuNameAndCallback(
                      navController = swipeDismissableNavController,
                      menuNameResource = R.string.dialogs_label,
                      screen = Screen.Dialogs
                    ),
                    menuNameAndCallback(
                      navController = swipeDismissableNavController,
                      menuNameResource = R.string.progress_indicators_label,
                      screen = Screen.ProgressIndicators
                    ),
                    menuNameAndCallback(
                      navController = swipeDismissableNavController,
                      menuNameResource = R.string.theme_label,
                      screen = Screen.Theme
                    ))

                    LandingScreen(
                      scalingLazyListState = scalingLazyListState,
                      focusRequester = focusRequester,
                      onClickWatchList = {
                        swipeDismissableNavController.navigate(Screen.WatchList.route)
                      },
                      menuItems = menuItems,
                      proceedingTimeTextEnabled = showProceedingTextBeforeTime,
                      onClickProceedingTimeText = {
                        showProceedingTextBeforeTime = !showProceedingTextBeforeTime
                      }
                    )
                  RequestFocusOnResume(focusRequester)
                }

                composable(
                  route = Screen.UserInputComponents.route,
                  arguments = listOf(
                    // In this case, the argument isn't part of the route, it's just attached
                    // as information for the destination.
                    navArgument(SCROLL_TYPE_NAV_ARGUMENT) {
                      type = NavType.EnumType(DestinationScrollType::class.java)
                      defaultValue = DestinationScrollType.SCALING_LAZY_COLUMN_SCROLLING
                    }
                  )
                ) {
                  val scalingLazyListState = scalingLazyListState(it)
                  val focusRequester = remember { FocusRequester() }

                  UserInputComponentsScreen(
                    scalingLazyListState = scalingLazyListState,
                    focusRequester = focusRequester,
                    value = displayValueForUserInput,
                    dateTime = dateTimeForUserInput,
                    onClickStepper = {
                      swipeDismissableNavController.navigate(Screen.Stepper.route)
                    },
                    onClickSlider = {
                      swipeDismissableNavController.navigate(Screen.Slider.route)
                    },
                    onClickDemoDatePicker = {
                      swipeDismissableNavController.navigate(Screen.DatePicker.route)
                    },
                    onClickDemo12hTimePicker = {
                      swipeDismissableNavController.navigate(Screen.Time12hPicker.route)
                    },
                    onClickDemo24hTimePicker = {
                      swipeDismissableNavController.navigate(Screen.Time24hPicker.route)
                    })

                    RequestFocusOnResume(focusRequester)
                }

                composable(route = Screen.Stepper.route) {
                  StepperScreen(
                    displayValue = displayValueForUserInput,
                    onValueChange = {
                      displayValueForUserInput = it
                    }
                  )
                }

                composable(route = Screen.Slider.route) {
                  SliderScreen(
                    displayValue = displayValueForUserInput,
                    onValueChange = {
                      displayValueForUserInput = it
                    }
                  )
                }

                composable(
                  route = Screen.WatchList.route,
                  arguments = listOf(
                    // In this case, the argument isn't part of the route, it's just attached
                    // as information for the destination.
                    navArgument(SCROLL_TYPE_NAV_ARGUMENT) {
                      type = NavType.EnumType(DestinationScrollType::class.java)
                      defaultValue = DestinationScrollType.SCALING_LAZY_COLUMN_SCROLLING
                    }
                  )
                ) {
                  val scalingLazyListState = scalingLazyListState(it)
                  val focusRequester = remember { FocusRequester() }

                  val viewModel: MessageListViewModel = viewModel(
                    factory = MessageListViewModel.Factory
                  )

                  MessageListScreen(
                    viewModel = viewModel,
                    scalingLazyListState = scalingLazyListState,
                    focusRequester = focusRequester,
                    showVignette = vignetteVisiblePreference,
                    onClickVignetteToggle = { showVignette ->
                      vignetteVisiblePreference = showVignette
                    },
                    onClickWatch = { id ->
                      swipeDismissableNavController.navigate(route = Screen.WatchDetail.route + "/" + id)
                    }
                  )
                  RequestFocusOnResume(focusRequester)
                }

                composable(
                  route = Screen.WatchDetail.route + "/{$WATCH_ID_NAV_ARGUMENT}",
                  arguments = listOf(
                    navArgument(WATCH_ID_NAV_ARGUMENT) {
                      type = NavType.IntType
                    },
                    // In this case, the argument isn't part of the route, it's just attached
                    // as information for the destination.
                    navArgument(SCROLL_TYPE_NAV_ARGUMENT) {
                      type = NavType.EnumType(DestinationScrollType::class.java)
                      defaultValue = DestinationScrollType.COLUMN_SCROLLING
                    }
                  )
                ) {
                  val watchId: Int = it.arguments!!.getInt(WATCH_ID_NAV_ARGUMENT)

                  val viewModel: MessageDetailViewModel = viewModel(factory = MessageDetailViewModel.factory(watchId))

                  val scrollState = scrollState(it)
                  val focusRequester = remember { FocusRequester() }

                  MessageDetailScreen(
                    viewModel = viewModel,
                    scrollState = scrollState,
                    focusRequester = focusRequester
                  )

                  RequestFocusOnResume(focusRequester)
                }

                composable(Screen.DatePicker.route) {
                  DatePicker(
                    onDateConfirm = {
                      swipeDismissableNavController.popBackStack()
                      dateTimeForUserInput = it.atTime(dateTimeForUserInput.toLocalTime())
                    },
                    date = dateTimeForUserInput.toLocalDate()
                  )
                }

                composable(Screen.Time24hPicker.route) {
                  TimePicker(
                    onTimeConfirm = {
                      swipeDismissableNavController.popBackStack()
                      dateTimeForUserInput = it.atDate(dateTimeForUserInput.toLocalDate())
                    },
                    time = dateTimeForUserInput.toLocalTime()
                  )
                }

                composable(Screen.Time12hPicker.route) {
                  TimePickerWith12HourClock(
                    onTimeConfirm = {
                      swipeDismissableNavController.popBackStack()
                      dateTimeForUserInput = it.atDate(dateTimeForUserInput.toLocalDate())
                    },
                    time = dateTimeForUserInput.toLocalTime()
                  )
                }

                composable(Screen.Dialogs.route) {
                  Dialogs()
                }

                composable(
                  route = Screen.ProgressIndicators.route,
                  arguments = listOf(
                    // In this case, the argument isn't part of the route, it's just attached
                    // as information for the destination.
                    navArgument(SCROLL_TYPE_NAV_ARGUMENT) {
                      type = NavType.EnumType(DestinationScrollType::class.java)
                      defaultValue = DestinationScrollType.SCALING_LAZY_COLUMN_SCROLLING
                    }
                  )
                ) {
                  val scalingLazyListState = scalingLazyListState(it)

                  val focusRequester = remember { FocusRequester() }
                  val menuItems = listOf(
                    menuNameAndCallback(
                      navController = swipeDismissableNavController,
                      menuNameResource = R.string.indeterminate_progress_indicator_label,
                      screen = Screen.IndeterminateProgressIndicator
                    ),
                    menuNameAndCallback(
                      navController = swipeDismissableNavController,
                      menuNameResource = R.string.full_screen_progress_indicator_label,
                      screen = Screen.FullScreenProgressIndicator
                    )
                  )
                  ProgressIndicatorsScreen(
                    scalingLazyListState = scalingLazyListState,
                    focusRequester = focusRequester,
                    menuItems = menuItems
                  )
                  RequestFocusOnResume(focusRequester)
                }

                composable(Screen.IndeterminateProgressIndicator.route) {
                  IndeterminateProgressIndicator()
                }

                composable(
                  route = Screen.FullScreenProgressIndicator.route,
                  arguments = listOf(
                    // In this case, the argument isn't part of the route, it's just attached
                    // as information for the destination.
                    navArgument(SCROLL_TYPE_NAV_ARGUMENT) {
                      type = NavType.EnumType(DestinationScrollType::class.java)
                      defaultValue = DestinationScrollType.TIME_TEXT_ONLY
                    }
                  )
                ) {
                  FullScreenProgressIndicator()
                }

                composable(
                  route = Screen.Theme.route,
                  arguments = listOf(
                    // In this case, the argument isn't part of the route, it's just attached
                    // as information for the destination.
                    navArgument(SCROLL_TYPE_NAV_ARGUMENT) {
                      type = NavType.EnumType(DestinationScrollType::class.java)
                      defaultValue = DestinationScrollType.SCALING_LAZY_COLUMN_SCROLLING
                    }
                  )
                ) { it ->
                  val scalingLazyListState = scalingLazyListState(it)
                  val focusRequester = remember { FocusRequester() }

                  ThemeScreen(
                    scalingLazyListState = scalingLazyListState,
                    focusRequester = focusRequester,
                    currentlySelectedColors = themeColors,
                    availableThemes = themeValues
                  ) { colors -> themeColors = colors }
                  RequestFocusOnResume(focusRequester)
                }

                activity(
                  route = Screen.Map.route
                ) {
                  this.activityClass = MapActivity::class
                }
            }
        }
    }
}

@Composable
private fun menuNameAndCallback(
  navController: NavHostController,
  menuNameResource: Int,
  screen: Screen
) = MenuItem(stringResource(menuNameResource)) { navController.navigate(screen.route) }

@Composable
private fun scrollState(it: NavBackStackEntry): ScrollState {
  val passedScrollType = it.arguments?.getSerializable(SCROLL_TYPE_NAV_ARGUMENT)

  check(passedScrollType == DestinationScrollType.COLUMN_SCROLLING) {
    "Scroll type must be DestinationScrollType.COLUMN_SCROLLING"
  }

  val scrollViewModel: ScrollStateViewModel = viewModel(it)
  return scrollViewModel.scrollState
}

@Composable
private fun scalingLazyListState(it: NavBackStackEntry): ScalingLazyListState {
  val passedScrollType = it.arguments?.getSerializable(SCROLL_TYPE_NAV_ARGUMENT)

  check(
    passedScrollType == DestinationScrollType.SCALING_LAZY_COLUMN_SCROLLING
  ) {
    "Scroll type must be DestinationScrollType.SCALING_LAZY_COLUMN_SCROLLING"
  }

  val scrollViewModel: ScalingLazyListStateViewModel = viewModel(it)

  return scrollViewModel.scrollState
}

@Composable
private fun RequestFocusOnResume(focusRequester: FocusRequester) {
  val lifecycleOwner = LocalLifecycleOwner.current
  LaunchedEffect(Unit) {
    lifecycleOwner.repeatOnLifecycle(state = Lifecycle.State.RESUMED) {
      focusRequester.requestFocus()
    }
  }
}

data class MenuItem(val name: String, val clickHander: () -> Unit)
