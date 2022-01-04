package net.igsoft.tablevis.util

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class UtilsTest {

    @Test
    fun `DistributeEvenly should correctly distribute adjustment`() {
        assertThat(Utils.distributeEvenly(5, 20)).isEqualTo(listOf(4, 4, 4, 4, 4))
        assertThat(Utils.distributeEvenly(5, 21)).isEqualTo(listOf(4, 4, 4, 4, 5))
        assertThat(Utils.distributeEvenly(0, 100)).isEqualTo(emptyList())
    }

    @Test
    fun `Assert that justify() properly justify text`() {
        assertThat(Text.justifyLine("\tAla i Maciek", 20)).isEqualTo("\tAla     i    Maciek")
    }
}
