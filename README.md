# Bakery Scanner Tool

A mobile application that utilizes object detection and machine learning models to scan bakery products without the need for serial numbers or barcodes.

## Problem Statement
Traditional labeling of bakery products introduces significant overhead. This application automates the identification process, reducing the need for manual labeling and minimizing operational costs.

## Installation
To get the latest version of the application, download the APK provided in the [releases](../../releases) section of this repository.

## Technical Documentation
Detailed insights into the system's architecture and design:

- **Design Documents:**
  - [Class Diagram](documentation/DetailedDesign.md)
  - System Design (WIP)
  - Component Design (WIP)
  - Usability & UX (WIP)

- **Functional Specifications:**
  - Application Start-up Flow
  - Product Scanning Workflow

## Project Structure & Dependencies
This application is designed as a modular component within a broader ecosystem.

### Key External Libraries
- **UI & Navigation:** Jetpack Compose, Compose Navigation, Material Design 3.
- **Camera & Vision:** CameraX (Core, Lifecycle, View), ML Kit (Text Recognition).
- **Inference Engine:** ONNX Runtime for Android.

### Machine Learning Models
- **Object Detection:** Utilizes a **YOLOv8** model for real-time item localization. [Learn more](https://docs.ultralytics.com/models/yolov8/).
- **Product Classification:** Employs a custom **PyTorch** model specialized in bakery product recognition. [Model Repository](https://github.com/Portfolio-Xin-Wang/BakkeryModel).

---
*Developed by Xin Wang*