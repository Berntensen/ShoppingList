package com.example.einkaufszettel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MaterialTheme { ShoppingScreen() } }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingScreen(vm: ShoppingViewModel = viewModel()) {
    val all by vm.articles.collectAsState()
    val shopping by vm.shopping.collectAsState()
    var input by remember { mutableStateOf("") }
    var categoryTarget by remember { mutableStateOf<Article?>(null) }

    val open = all.filter { !it.done }
    val inCart = all.filter { it.done }

    fun submit() {
        vm.add(input)
        input = ""
    }

    categoryTarget?.let { target ->
        AlertDialog(
            onDismissRequest = { categoryTarget = null },
            title = { Text(stringResource(R.string.dialog_choose_category)) },
            text = {
                Column {
                    LazyColumn(Modifier.heightIn(max = 360.dp)) {
                        items(Category.entries.filter { it != Category.OTHER }) { cat ->
                            Text(
                                stringResource(cat.labelRes),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        vm.setCategory(target, cat)
                                        categoryTarget = null
                                    }
                                    .padding(vertical = 12.dp)
                            )
                        }
                    }
                    HorizontalDivider()
                    Text(
                        stringResource(Category.OTHER.labelRes),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                vm.setCategory(target, Category.OTHER)
                                categoryTarget = null
                            }
                            .padding(vertical = 12.dp)
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { categoryTarget = null }) {
                    Text(stringResource(R.string.btn_cancel))
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(if (shopping) R.string.title_shopping else R.string.title_planning))
                }
            )
        }
    ) { padding ->
        Column(Modifier.padding(padding).padding(horizontal = 16.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = input,
                    onValueChange = { input = it },
                    label = { Text(stringResource(R.string.label_article)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { submit() }),
                    modifier = Modifier.weight(1f)
                )
                Spacer(Modifier.width(8.dp))
                FilledIconButton(onClick = { submit() }) {
                    Icon(Icons.Default.Add, contentDescription = stringResource(R.string.btn_add))
                }
            }

            Button(
                onClick = { if (shopping) vm.finishShopping() else vm.startShopping() },
                modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp)
            ) {
                Text(stringResource(if (shopping) R.string.btn_finish_shopping else R.string.btn_start_shopping))
            }

            if (all.isEmpty()) {
                Text(stringResource(R.string.hint_empty), style = MaterialTheme.typography.bodyMedium)
            }

            LazyColumn {
                Category.entries.forEach { cat ->
                    val inCategory = open.filter { Category.fromId(it.categoryId) == cat }
                    if (inCategory.isNotEmpty()) {
                        item(key = "header_${cat.name}") {
                            Text(
                                stringResource(cat.labelRes),
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
                            )
                        }
                        items(inCategory, key = { it.id }) { a ->
                            ListItem(
                                headlineContent = { Text(a.name) },
                                supportingContent = if (!shopping && cat == Category.OTHER) {
                                    {
                                        Text(
                                            stringResource(R.string.hint_assign_category),
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                } else null,
                                leadingContent = {
                                    if (a.quantity > 1) Text(stringResource(R.string.quantity_format, a.quantity))
                                },
                                trailingContent = {
                                    if (!shopping) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            IconButton(onClick = { vm.changeQuantity(a, -1) }) { Text("−") }
                                            IconButton(onClick = { vm.changeQuantity(a, +1) }) { Text("+") }
                                            IconButton(onClick = { vm.delete(a.id) }) {
                                                Icon(Icons.Default.Delete, contentDescription = stringResource(R.string.btn_delete))
                                            }
                                        }
                                    }
                                },
                                modifier = Modifier.clickable {
                                    if (shopping) vm.setDone(a.id, true) else categoryTarget = a
                                }
                            )
                            HorizontalDivider()
                        }
                    }
                }

                if (shopping && inCart.isNotEmpty()) {
                    item {
                        Text(
                            stringResource(R.string.section_in_cart),
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.padding(top = 24.dp, bottom = 4.dp)
                        )
                    }
                    items(inCart, key = { it.id }) { a ->
                        ListItem(
                            headlineContent = {
                                Text(a.name, textDecoration = TextDecoration.LineThrough)
                            },
                            modifier = Modifier.clickable { vm.setDone(a.id, false) }
                        )
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}