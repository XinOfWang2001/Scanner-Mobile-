# Project Structure

## Global structure

```text
root/
├── app/
│   ├── build.gradle.kts
│   ├── proguard-rules.pro
│   ├── src/
│   ├── build/
│   └── ai_instructions/
├── gradle/
│   └── libs.versions.toml
├── _ai_instructions/
│   ├── AGENTS.md
│   └── project.md
├── build.gradle.kts
├── gradle.properties
├── settings.gradle.kts
├── gradlew
├── gradlew.bat
└── local.properties
```

## Folder structure software

```text
app/src/
├── main/
│   ├── java/
│   │   └── com/ui/scannerapp/
│   │       ├── MainActivity.kt
│   │       ├── viewmodels/
│   │       │   └── ScannerViewModel.kt
│   │       ├── services/
│   │       │   ├── interfaces/
│   │       │   │   ├── IPredictionService.kt
│   │       │   │   └── IProductService.kt
│   │       │   └── implementations/
│   │       │       ├── OnlineModelService.kt
│   │       │       ├── LocalModelService.kt
│   │       │       └── DeviceStateManager.kt
│   │       ├── entities/
│   │       │   ├── dto/
│   │       │   │   ├── PredictionDTO.kt
│   │       │   │   └── ProductDTO.kt
│   │       │   └── domain/
│   │       │       ├── Prediction.kt
│   │       │       ├── Product.kt
│   │       │       └── Checkout.kt
│   │       └── pages/
│   │           ├── MainActivity.kt
│   │           ├── home/
│   │           │   └── Home.kt
│   │           ├── theme/
│   │           │   ├── Color.kt
│   │           │   ├── Theme.kt
│   │           │   ├── Type.kt
│   │           │   └── constant.kt
│   │           ├── ui/
│   │           ├── shared/
│   │           └── CheckoutScreen/
│   │               ├── CheckoutScreen.kt
│   │               ├── InferenceModel.kt
│   │               ├── example/
│   │               │   ├── MessageCard.kt
│   │               │   └── SampleData.kt
│   │               └── ScannerFeature/
│   │                   ├── CameraPreview.kt
│   │                   └── VisionFunction.kt
│   ├── res/
│   │   ├── drawable/
│   │   ├── drawable-ldpi/
│   │   ├── mipmap-hdpi/
│   │   ├── mipmap-mdpi/
│   │   ├── mipmap-xhdpi/
│   │   ├── mipmap-xxhdpi/
│   │   ├── mipmap-xxxhdpi/
│   │   ├── mipmap-anydpi-v26/
│   │   ├── values/
│   │   └── xml/
│   └── AndroidManifest.xml
├── test/
│   └── java/
└── androidTest/
    └── java/
```

## Dependencies
- androidx.core.ktx
- androidx.lifecycle.runtime.ktx
- androidx.activity.compose
- androidx.compose.bom
- androidx.compose.ui
- androidx.compose.ui.graphics
- androidx.compose.ui.tooling.preview
- androidx.compose.material3
- androidx.navigation.compose
- androidx.camera.core
- androidx.camera.lifecycle
- androidx.camera.view
- androidx.camera.mlkit.vision
- com.google.mlkit:vision-common
- com.google.android.gms:play-services-mlkit-text-recognition-common
- com.google.android.gms:play-services-mlkit-text-recognition
- org.pytorch:pytorch_android_lite
- org.pytorch:pytorch_android_torchvision_lite
