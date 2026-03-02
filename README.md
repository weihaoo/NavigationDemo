# NavigationDemo

A demo app that explores a Jetpack Compose Navigation question from a mobile module i am currently taking. It demonstrates what actually happens when you navigate to `"details_screen"` while the registered composable route is `"details_screen/{name}"`.

**TLDR:** It crashes with an `IllegalArgumentException` because the routes don't match.

## The Question

Given this code:

```kotlin
NavHost(navController, startDestination = "home_screen") {
    composable("home_screen") {
        Button(onClick = { navController.navigate("details_screen") }) {
            Text("Go to Details")
        }
    }
    composable("details_screen/{name}") { backStackEntry ->
        val name = backStackEntry.arguments?.getString("name") ?: "Guest"
        Text("Hello, $name!")
    }
}
```

**What happens when you click "Go to Details"?**

- A) `Hello, Guest!`
- B) Error
- C) `Hello, INF2007!`
- D) `Hello, Android!`

**Correct answer: B) Error**

## What This App Does

The app replicates the exact quiz scenario. Clicking "Go to Details" calls `navController.navigate("details_screen")`, which throws `IllegalArgumentException`. The app catches the exception and displays the error message on screen in red text, confirming that the answer is **B) Error**.

See [`MainActivity.kt`](app/src/main/java/com/example/navigationdemo/MainActivity.kt) for the full implementation.
