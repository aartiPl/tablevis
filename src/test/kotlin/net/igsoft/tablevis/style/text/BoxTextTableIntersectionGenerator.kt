package net.igsoft.tablevis.style.text

class BoxTextTableIntersectionGenerator {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            println(2.toString(2).padStart(4, '0'))

            //----------------------------------------------------------------------------------------------------------
            val straightLightCornerIntersectionTemplates = buildMap {
                put(" │─ ", '└');
                put("─  │", '┐');
                put("  ─│", '┌');
                put("─│  ", '┘');
            }

            val straightLightCornerIntersection = calculateCombinations(
                straightLightCornerIntersectionTemplates, horizontalLightBorders,
                verticalLightBorders,
                horizontalHeavyBorders,
                verticalHeavyBorders,
            )

            printMap("private val straightLightCornerIntersection = buildMap {", straightLightCornerIntersection, "}")

            //----------------------------------------------------------------------------------------------------------

            val roundedLightCornerIntersectionTemplates = buildMap {
                put(" │─ ", '╰');
                put("─  │", '╮');
                put("  ─│", '╭');
                put("─│  ", '╯');
            }

            val roundedLightCornerIntersection = calculateCombinations(
                roundedLightCornerIntersectionTemplates, horizontalLightBorders,
                verticalLightBorders,
                horizontalHeavyBorders,
                verticalHeavyBorders,
            )

            printMap("private val roundedLightCornerIntersection = buildMap {", roundedLightCornerIntersection, "}")

            //----------------------------------------------------------------------------------------------------------
            val lightOnlyIntersectionsTemplates = buildMap {
                put(" │─│", '├'); put("─│ │", '┤'); put("─ ─│", '┬'); put("─│─ ", '┴')
                put("─│─│", '┼');
            }

            val lightOnlyIntersections = calculateCombinations(
                lightOnlyIntersectionsTemplates, horizontalLightBorders,
                verticalLightBorders,
                horizontalHeavyBorders,
                verticalHeavyBorders,
            )

            printMap("lightOnlyIntersections: {", lightOnlyIntersections, "}")

            //----------------------------------------------------------------------------------------------------------
            val heavyOnlyIntersectionsTemplate = buildMap {
                put("  ━┃", '┏'); put("━  ┃", '┓'); put("━┃  ", '┛'); put(" ┃━ ", '┗')
                put(" ┃━┃", '┣'); put("━┃ ┃", '┫'); put("━ ━┃", '┳'); put("━┃━ ", '┻')
                put("━┃━┃", '╋');
            }

            val heavyOnlyIntersections = calculateCombinations(
                heavyOnlyIntersectionsTemplate, horizontalLightBorders,
                verticalLightBorders,
                horizontalHeavyBorders,
                verticalHeavyBorders,
            )

            printMap("heavyOnlyIntersections: {", heavyOnlyIntersections, "}")

            //----------------------------------------------------------------------------------------------------------

            val noneIntersectionTemplates = buildMap {
                put("│   ", ' '); put(" │  ", ' '); put("  │ ", ' '); put("   │", ' ');
                put("─   ", ' '); put(" ─  ", ' '); put("  ─ ", ' '); put("   ─", ' ');
                put("─ ─ ", '─'); put(" │ │", '│');
                put("    ", ' ')
            }

            val noneIntersections = calculateCombinations(
                noneIntersectionTemplates,
                horizontalLightBorders,
                verticalLightBorders,
                horizontalHeavyBorders,
                verticalHeavyBorders,
            )

            printMap("noneIntersections: {", noneIntersections, "}")

            //----------------------------------------------------------------------------------------------------------

            val mixedIntersectionTemplates = buildMap {
                put(" ┃─ ", '┖')
                put(" │━ ", '┕')
                put("─┃  ", '┚')
                put("━│  ", '┙')
                put("━  │", '┑')
                put("─  ┃", '┒')
                put("  ━│", '┍')
                put("  ─┃", '┎')

                put(" │━┃", '┢');
                put(" ┃━│", '┡')
                put(" ┃─│", '┞')
                put(" │─┃", '┟')

                put("━┃ │", '┩')
                put("━│ ┃", '┪')
                put("─┃ │", '┦')
                put("─│ ┃", '┧')

                put("━│━ ", '┷')
                put("─┃─ ", '┸')
                put("─┃━ ", '┺')
                put("━┃─ ", '┹')

                put("━ ━│", '┯')
                put("━ ─│", '┭')
                put("━ ─┃", '┱')
                put("─ ━┃", '┲')

                put("━┃━│", '╇')
                put("━│━┃", '╈')
                put("━┃─┃", '╉')
                put("─┃━┃", '╊')
                put("─┃━│", '╄')
                put("─│━┃", '╆')
                put("━│─┃", '╅')
                put("━┃─│", '╃')

                put("━ ─ ", '╾')
                put("─ ━ ", '╼')
                put(" ┃ │", '╿')
                put(" │ ┃", '╽')
            }

            val mixedIntersections = calculateCombinations(
                mixedIntersectionTemplates,
                horizontalLightBorders,
                verticalLightBorders,
                horizontalHeavyBorders,
                verticalHeavyBorders,
            )

            printMap("mixedIntersections: {", mixedIntersections, "}")
        }

        private val horizontalLightBorders = listOf(
            BoxTextTableStyleSet.horizontalLightBorder,
            BoxTextTableStyleSet.horizontalQuadrupleLightDashedBorder,
            BoxTextTableStyleSet.horizontalTripleLightDashedBorder,
            BoxTextTableStyleSet.horizontalDoubleLightDashedBorder
        )
        private val horizontalHeavyBorders = listOf(
            BoxTextTableStyleSet.horizontalHeavyBorder,
            BoxTextTableStyleSet.horizontalQuadrupleHeavyDashedBorder,
            BoxTextTableStyleSet.horizontalTripleHeavyDashedBorder,
            BoxTextTableStyleSet.horizontalDoubleHeavyDashedBorder
        )

        private val verticalLightBorders = listOf(
            BoxTextTableStyleSet.verticalLightBorder,
            BoxTextTableStyleSet.verticalQuadrupleLightDashedBorder,
            BoxTextTableStyleSet.verticalTripleLightDashedBorder,
            BoxTextTableStyleSet.verticalDoubleLightDashedBorder
        )
        private val verticalHeavyBorders = listOf(
            BoxTextTableStyleSet.verticalHeavyBorder,
            BoxTextTableStyleSet.verticalQuadrupleHeavyDashedBorder,
            BoxTextTableStyleSet.verticalTripleHeavyDashedBorder,
            BoxTextTableStyleSet.verticalDoubleHeavyDashedBorder
        )

        private fun printMap(preamble: String, intersections: Map<String, Char>, postamble: String) {
            println(preamble)

            //Group map by resulting character
            val valueKeys = mutableMapOf<Char, MutableList<String>>()
            for (intersection in intersections) {
                val list = valueKeys.getOrPut(intersection.value) { mutableListOf() }
                list.add(intersection.key)
            }

            for (entry in valueKeys) {
                print("    ")

                for (value in entry.value) {
                    print("put(\"${value}\", '${entry.key}'); ")
                }

                println()
            }

            println("\n$postamble")
            println()
        }

        private fun calculateCombinations(
            templates: Map<String, Char>,
            horizontalBorders: List<TextTableBorder>,
            verticalBorders: List<TextTableBorder>,
            horizontalHeavyBorders: List<TextTableBorder>,
            verticalHeavyBorders: List<TextTableBorder>
        ): MutableMap<String, Char> {
            val intersections = mutableMapOf<String, Char>()

            for (template in templates) {
                for (i in 0..15) {
                    val binary = i.toString(2).padStart(4)

                    val previousKeys = mutableSetOf(template.key)
                    for (pos in 0..3) {

                        if (binary.elementAt(pos) == '1') {

                            when (template.key[pos]) {
                                '│' -> {
                                    verticalBorders.forEach {
                                        val newKeys = mutableSetOf<String>()
                                        for (key in previousKeys) {
                                            val newKey = replaceAtPosition(key, pos, it.line[0])
                                            intersections[newKey] = template.value
                                            newKeys.add(newKey)
                                        }
                                        previousKeys.addAll(newKeys)
                                    }
                                }
                                '┃' -> {
                                    verticalHeavyBorders.forEach {
                                        val newKeys = mutableSetOf<String>()
                                        for (key in previousKeys) {
                                            val newKey = replaceAtPosition(key, pos, it.line[0])
                                            intersections[newKey] = template.value
                                            newKeys.add(newKey)
                                        }
                                        previousKeys.addAll(newKeys)
                                    }
                                }

                                '─' -> {
                                    horizontalBorders.forEach {
                                        val newKeys = mutableSetOf<String>()
                                        for (key in previousKeys) {
                                            val newKey = replaceAtPosition(key, pos, it.line[0])
                                            intersections[newKey] = template.value
                                            newKeys.add(newKey)
                                        }
                                        previousKeys.addAll(newKeys)
                                    }
                                }

                                '━' -> {
                                    horizontalHeavyBorders.forEach {
                                        val newKeys = mutableSetOf<String>()
                                        for (key in previousKeys) {
                                            val newKey = replaceAtPosition(key, pos, it.line[0])
                                            intersections[newKey] = template.value
                                            newKeys.add(newKey)
                                        }
                                        previousKeys.addAll(newKeys)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            return intersections
        }

        private fun replaceAtPosition(string: String, index: Int, char: Char): String {
            val chars = string.toCharArray()
            chars[index] = char
            return String(chars)
        }
    }
}
