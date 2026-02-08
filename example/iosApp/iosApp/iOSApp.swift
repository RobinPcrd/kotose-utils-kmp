/*
* Copyright 2025-present Robin PICARD and contributors. Use of this source code is governed by the Apache 2.0 license.
*/

import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    init() {
        AppInitKt.doInitKotoseUtils { config in
            config.stringResourceResolver { key in
                Res.shared.allStringResources[key]
            }
            config.pluralStringResourceResolver { key in
                Res.shared.allPluralStringResources[key]
            }
        }
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
