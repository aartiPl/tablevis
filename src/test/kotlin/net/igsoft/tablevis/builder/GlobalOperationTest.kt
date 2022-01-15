package net.igsoft.tablevis.builder

import assertk.assertThat
import assertk.assertions.containsExactly
import org.junit.jupiter.api.Test

class GlobalOperationTest {

    @Test
    fun `Test generation of ids`() {
        assertThat(GlobalOperation.generateIds(5)).containsExactly("col-1", "col-2", "col-3", "col-4", "col-5")
        assertThat(GlobalOperation.generateIds(0)).containsExactly()
        assertThat(GlobalOperation.generateIds(-1)).containsExactly()
        assertThat(GlobalOperation.generateIds(1)).containsExactly("col-1")
    }
}
