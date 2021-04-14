package com.brokoli.rules

import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Issue
import org.junit.jupiter.api.Test

@Suppress("UnstableApiUsage")
class KotlinShouldNotThrowExceptionsDetectorTest : AndroidSdkLintDetectorTest() {

    override fun getDetector(): Detector = KotlinShouldNotThrowExceptionsDetector()

    override fun getIssues(): MutableList<Issue> = mutableListOf(KotlinShouldNotThrowExceptionsDetector.ISSUE)

    fun testdetectJavaDoesNotThrowExceptions() {
        val javaFile = java(
            """
            package com.brokoli.lint;

            class MyClass {
                
                public void method() {
                    otherMethod();
                }
                
                private void otherMethod() {
                
                }
                
            }
        """
        ).indented()

        val lintResult = lint()
            .files(javaFile)
            .run()

        lintResult.expectClean()
    }

    fun testdetectJavaThrowsOneException() {
        val javaFile = java(
            """
            package com.brokoli.lint;
            
            import java.io.IOException;

            class MyClass {
                
                public void method() throws IOException {
                    throw new IOException();
                }
                
            }
        """
        ).indented()

        val lintResult = lint()
            .files(javaFile)
            .run()

        lintResult.expectClean()
    }

    fun testdetectKotlinDoesNotThrowExceptions() {
        val kotlinFile = kotlin(
            """
            package com.brokoli.lint

            class MyClass {
                
                fun method() {
                    otherMethod()
                }
                
                private fun otherMethod() {
                
                }
                
            }
        """
        ).indented()

        val lintResult = lint()
            .files(kotlinFile)
            .run()

        lintResult.expectClean()
    }

    fun testdetectKotlinHasOneThrowsAnnotation() {
        val kotlinFile = kotlin(
            """
            package com.brokoli.lint
            
            import java.io.IOException

            class MyClass {
                
                @Throws(IOException::class)
                fun method() {
                    otherMethod()
                }
                
                private fun otherMethod() {
                
                }
                
            }
        """
        ).indented()

        val lintResult = lint()
            .files(kotlinFile)
            .run()

        lintResult
            .expectErrorCount(1)
            .expect(
                """
             src/com/brokoli/lint/MyClass.kt:7: Error: Kotlin code should not throw Exceptions [KotlinShouldNotThrowExceptionsDetector]
                 @Throws(IOException::class)
                 ^
             1 errors, 0 warnings
         """.trimIndent()
            )
    }

    fun testdetectKotlinHasTwoThrowsAnnotations() {
        val kotlinFile = kotlin(
            """
            package com.brokoli.lint
            
            import java.io.IOException
            import java.lang.NullPointerException

            class MyClass {
                
                @Throws(IOException::class)
                fun method() {
                    otherMethod()
                }
                
                @Throws(NullPointerException::class)
                private fun otherMethod() {
                
                }
                
            }
        """
        ).indented()

        val lintResult = lint()
            .files(kotlinFile)
            .run()

        lintResult
            .expectErrorCount(2)
            .expect(
                """
             src/com/brokoli/lint/MyClass.kt:8: Error: Kotlin code should not throw Exceptions [KotlinShouldNotThrowExceptionsDetector]
                 @Throws(IOException::class)
                 ^
             src/com/brokoli/lint/MyClass.kt:13: Error: Kotlin code should not throw Exceptions [KotlinShouldNotThrowExceptionsDetector]
                 @Throws(NullPointerException::class)
                 ^
             2 errors, 0 warnings
         """.trimIndent()
            )
    }

    fun testdetectKotlinThrowsException() {
        val kotlinFile = kotlin(
            """
            package com.brokoli.lint
            
            import java.lang.Exception

            class MyClass {
                
                fun method() {
                    throw Exception()
                }
                
            }
        """
        ).indented()

        val lintResult = lint()
            .files(kotlinFile)
            .run()

        lintResult
            .expectErrorCount(1)
            .expect(
                """
             src/com/brokoli/lint/MyClass.kt:8: Error: Kotlin code should not throw Exceptions [KotlinShouldNotThrowExceptionsDetector]
                     throw Exception()
                           ~~~~~~~~~~~
             1 errors, 0 warnings
         """.trimIndent()
            )
    }

    fun testdetectKotlinThrowsCheckedException() {
        val kotlinFile = kotlin(
            """
            package com.brokoli.lint
            
            import java.io.IOException

            class MyClass {
                
                fun method() {
                    throw IOException()
                }
                
            }
        """
        ).indented()

        val lintResult = lint()
            .files(kotlinFile)
            .run()

        lintResult
            .expectErrorCount(1)
            .expect(
                """
             src/com/brokoli/lint/MyClass.kt:8: Error: Kotlin code should not throw Exceptions [KotlinShouldNotThrowExceptionsDetector]
                     throw IOException()
                           ~~~~~~~~~~~~~
             1 errors, 0 warnings
         """.trimIndent()
            )
    }

    fun testdetectKotlinThrowsRuntimeException() {
        val kotlinFile = kotlin(
            """
            package com.brokoli.lint

            import java.lang.RuntimeException

            class MyClass {
                
                fun method() {
                    throw RuntimeException()
                }
                
            }
        """
        ).indented()

        val lintResult = lint()
            .files(kotlinFile)
            .run()

        lintResult.expectClean()
    }

    fun testdetectKotlinThrowsUncheckedException() {
        val kotlinFile = kotlin(
            """
            package com.brokoli.lint

            import java.lang.IllegalStateException

            class MyClass {
                
                fun method() {
                    throw IllegalStateException()
                }
                
            }
        """
        ).indented()

        val lintResult = lint()
            .files(kotlinFile)
            .run()

        lintResult.expectClean()
    }

}