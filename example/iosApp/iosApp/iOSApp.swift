/*
* Copyright 2025-present Robin PICARD and contributors. Use of this source code is governed by the Apache 2.0 license.
*/

import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    init() {
        AppInitKt.doInitKotoseUtils { config in
        }
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
