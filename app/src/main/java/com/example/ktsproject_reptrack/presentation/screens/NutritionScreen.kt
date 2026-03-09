package com.example.ktsproject_reptrack.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ktsproject_reptrack.R
import com.example.ktsproject_reptrack.presentation.components.BottomNavBar
import com.example.ktsproject_reptrack.presentation.components.NutritionErrorSection
import com.example.ktsproject_reptrack.presentation.components.NutritionInputSection
import com.example.ktsproject_reptrack.presentation.components.NutritionLoadingIndicator
import com.example.ktsproject_reptrack.presentation.components.NutritionResultCard
import com.example.ktsproject_reptrack.presentation.components.NutritionSearchHeader
import com.example.ktsproject_reptrack.presentation.viewmodel.NutritionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NutritionScreen(
    modifier: Modifier = Modifier,
    viewModel: NutritionViewModel,
    onBackClick: () -> Unit,
    onNavigateToMain: () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current

    LaunchedEffect(uiState.result) {
        if (uiState.result != null) {
            focusManager.clearFocus()
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.auth_title),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        bottomBar = {
            BottomNavBar(
                currentRoute = "nutrition",
                onNavigate = { route ->
                    when (route) {
                        "main" -> onNavigateToMain()
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            NutritionSearchHeader()

            Spacer(modifier = Modifier.height(8.dp))

            NutritionInputSection(
                productName = uiState.productName,
                grams = uiState.grams,
                onProductNameChange = { viewModel.onProductNameChange(it) },
                onGramsChange = { viewModel.onGramsChange(it) },
                isLoading = uiState.isLoading
            )

            NutritionErrorSection(
                error = uiState.error,
                onRetry = { viewModel.onRetry() }
            )

            if (uiState.isLoading) {
                NutritionLoadingIndicator()
            }

            AnimatedVisibility(
                visible = uiState.result != null,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                uiState.result?.let { result ->
                    NutritionResultCard(
                        result = result,
                        onClear = { viewModel.onClear() }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
