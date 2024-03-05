package com.frameworkium.lite.ui.capture

import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.StaleElementReferenceException
import org.openqa.selenium.WebElement
import spock.lang.Specification

class ElementHighlighterSpec extends Specification {

    JavascriptExecutor mockJSE = Mock(JavascriptExecutor)
    def mockElement = Mock(WebElement)

    ElementHighlighter sut = new ElementHighlighter(mockJSE)

    def "provided element is highlighted and same element is un-highlighted"() {
        when: "We highlight then un-highlight an element"
            sut.highlightElement(mockElement)
            sut.unhighlightPrevious()
        then: "The correct scripts are executed against the given element"
            1 * mockJSE.executeScript(ElementHighlighter.HIGHLIGHT_ELEMENT_SCRIPT, mockElement)
            1 * mockJSE.executeScript(ElementHighlighter.UNHIGHLIGHT_ELEMENT_SCRIPT, mockElement)
    }

    def "StaleElementReferenceException's are caught"() {
        when: "We highlight then un-highlight an element"
            sut.highlightElement(mockElement)
            sut.unhighlightPrevious()
        then: "StaleElementReferenceException are not thrown"
            2 * mockJSE.executeScript(_ as String, mockElement) >> {
                throw new StaleElementReferenceException("")
            }
            notThrown(StaleElementReferenceException)
    }
}
