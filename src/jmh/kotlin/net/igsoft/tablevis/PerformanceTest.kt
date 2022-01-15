package net.igsoft.tablevis

import com.copperleaf.krow.builder.bodyRow
import com.copperleaf.krow.builder.krow
import com.copperleaf.krow.formatters.ascii.AsciiTableFormatter
import net.igsoft.tablevis.printer.text.TextTablePrinter
import net.igsoft.tablevis.style.text.BoxTextTableStyleSet
import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole
import org.openjdk.jmh.runner.Runner
import org.openjdk.jmh.runner.options.OptionsBuilder
import java.util.concurrent.TimeUnit

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
open class PerformanceTest {
    @Benchmark
    fun tablevisLibrary(blackhole: Blackhole) {
        val table = TableBuilder(BoxTextTableStyleSet()) {
            row {
                cell { value = "Nice title 1" }
                cell { value = "Nice title 2" }
                cell { value = "Nice title 3" }
                syncColumns()
            }
        }.build()
        blackhole.consume(TextTablePrinter().print(table))
    }

    @Benchmark
    fun krowLibrary(blackhole: Blackhole) {
        val table = krow {
            includeHeaderRow = false
            includeLeadingColumn = false
            bodyRow {
                cell("Nice title 1") { }
                cell("Nice title 2") { }
                cell("Nice title 3") { }
            }
        }
        blackhole.consume(AsciiTableFormatter().print(table))
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val opt =
                OptionsBuilder().include(PerformanceTest::class.java.simpleName)
                    .warmupIterations(1)
                    .measurementIterations(1)
                    .forks(1)
                    .build()

            Runner(opt).run()
        }
    }
}
