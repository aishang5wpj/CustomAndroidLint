# Custom Lint Rules

## Current rules

* Method should not have more than 5 parameters
* Do not throw Exception from Kotlin code (either by annotating the method as @Throws or by actually throwing a new Exception)
* Detect if Java code throws and Kotlin code does not catch

## Ideas

* Detect if we are using immutable list/set/map and Java needs a mutable list/set/map

## Resources

* Android Official Lint Doc: https://developer.android.com/studio/write/lint
* Android Official Sample: https://github.com/googlesamples/android-custom-lint-rules
* All of Android lint checks are available at https://android.googlesource.com/platform/tools/base/+/master/lint/libs/lint-checks/src/main/java/com/android/tools/lint/checks
* Initial code based on https://github.com/fabiocarballo/lint-sample
* Other custom lint rules:
    * https://www.bignerdranch.com/blog/building-custom-lint-checks-in-android/
    * https://jayrambhia.com/blog/android-lint and https://jayrambhia.com/blog/android-lint-ref
    * https://proandroiddev.com/enforcing-clean-architecture-using-android-custom-lint-rules-aa8fc1708c59
    * https://medium.com/supercharges-mobile-product-guide/formatting-code-analysis-rule-with-android-lint-part-1-2-4b906f717382
    * https://medium.com/@sinankozak/android-lint-rule-for-immutable-kotlin-data-classes-5c91517c7611