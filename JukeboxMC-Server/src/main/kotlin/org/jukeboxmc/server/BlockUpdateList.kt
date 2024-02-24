package org.jukeboxmc.server

import org.jukeboxmc.api.block.Block
import java.util.*


class BlockUpdateList {

    private var element: Element? = null

    @Synchronized
    fun addElement(timeValue: Long, block: Block) {
        if (element == null) {
            element = Element(timeValue, null, LinkedList<Block>().apply { add(block) })
        } else {
            var element = element
            var previousElement: Element? = null

            while (element != null && element.timeValue < timeValue) {
                previousElement = element
                element = element.nextElement
            }

            if (element == null) {
                previousElement?.let {
                    it.nextElement = Element(timeValue, null, LinkedList<Block>().apply { add(block) })
                }
            } else {
                if (element.timeValue != timeValue) {
                    val nextElement = Element(timeValue, element, LinkedList<Block>().apply { add(block) })

                    if (previousElement != null) {
                        previousElement.nextElement = nextElement
                    } else {
                        this.element = nextElement
                    }
                } else {
                    element.blockQueue.add(block)
                }
            }
        }
    }

    @Synchronized
    fun getNextTaskTime(): Long {
        return element?.timeValue ?: Long.MAX_VALUE
    }

    @Synchronized
    fun getNextElement(): Block? {
        if (element == null) {
            return null
        }

        while (element != null && element!!.blockQueue.isEmpty()) {
            element = element!!.nextElement
        }

        if (element == null) {
            return null
        }

        val block = element!!.blockQueue.poll()
        while (element!!.blockQueue.isEmpty()) {
            element = element!!.nextElement
            if (element == null) {
                break
            }
        }
        return block
    }

    class Element(
        var timeValue: Long,
        var nextElement: Element?,
        var blockQueue: Queue<Block>
    )
}
