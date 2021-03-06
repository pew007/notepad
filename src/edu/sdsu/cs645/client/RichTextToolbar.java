/*
 * This software is published under the Apchae 2.0 licenses.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Author: Erik Scholtz
 * Web: http://blog.elitecoderz.net
 */

package edu.sdsu.cs645.client;

import java.util.HashMap;

import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.RichTextArea.Formatter;

public class RichTextToolbar extends Composite {
    private static final String CSS_ROOT_NAME = "RichTextToolbar";

    //Color and Fontlists - First Value (key) is the Name to display, Second Value (value) is the HTML-Definition
    public final static HashMap<String, String> GUI_COLORLIST = new HashMap<String, String>();

    static {
        GUI_COLORLIST.put("White", "#FFFFFF");
        GUI_COLORLIST.put("Black", "#000000");
        GUI_COLORLIST.put("Red", "red");
        GUI_COLORLIST.put("Green", "green");
        GUI_COLORLIST.put("Yellow", "yellow");
        GUI_COLORLIST.put("Blue", "blue");
    }

    public final static HashMap<String, String> GUI_FONTLIST = new HashMap<String, String>();

    static {
        GUI_FONTLIST.put("Times New Roman", "Times New Roman");
        GUI_FONTLIST.put("Arial", "Arial");
        GUI_FONTLIST.put("Courier New", "Courier New");
        GUI_FONTLIST.put("Georgia", "Georgia");
        GUI_FONTLIST.put("Trebuchet", "Trebuchet");
        GUI_FONTLIST.put("Verdana", "Verdana");
    }

    //HTML Related (styles made by SPAN and DIV)
    private static final String HTML_STYLE_CLOSE_SPAN = "</span>";
    private static final String HTML_STYLE_CLOSE_DIV = "</div>";
    private static final String HTML_STYLE_OPEN_BOLD = "<span style=\"font-weight: bold;\">";
    private static final String HTML_STYLE_OPEN_ITALIC = "<span style=\"font-weight: italic;\">";
    private static final String HTML_STYLE_OPEN_UNDERLINE = "<span style=\"font-weight: underline;\">";
    private static final String HTML_STYLE_OPEN_LINETHROUGH = "<span style=\"font-weight: line-through;\">";
    private static final String HTML_STYLE_OPEN_ALIGNLEFT = "<div style=\"text-align: left;\">";
    private static final String HTML_STYLE_OPEN_ALIGNCENTER = "<div style=\"text-align: center;\">";
    private static final String HTML_STYLE_OPEN_ALIGNRIGHT = "<div style=\"text-align: right;\">";
    private static final String HTML_STYLE_OPEN_INDENTRIGHT = "<div style=\"margin-left: 40px;\">";

    //HTML Related (styles made by custom HTML-Tags)
    private static final String HTML_STYLE_OPEN_SUBSCRIPT = "<sub>";
    private static final String HTML_STYLE_CLOSE_SUBSCRIPT = "</sub>";
    private static final String HTML_STYLE_OPEN_SUPERSCRIPT = "<sup>";
    private static final String HTML_STYLE_CLOSE_SUPERSCRIPT = "</sup>";
    private static final String HTML_STYLE_OPEN_ORDERLIST = "<ol><li>";
    private static final String HTML_STYLE_CLOSE_ORDERLIST = "</ol></li>";
    private static final String HTML_STYLE_OPEN_UNORDERLIST = "<ul><li>";
    private static final String HTML_STYLE_CLOSE_UNORDERLIST = "</ul></li>";

    //HTML Related (styles without closing Tag)
    private static final String HTML_STYLE_HLINE = "<hr style=\"width: 100%; height: 2px;\">";

    //GUI Related stuff
    private static final String GUI_DIALOG_INSERTURL = "Enter a link URL:";
    private static final String GUI_DIALOG_IMAGEURL = "Enter an image URL:";

    private static final String GUI_LISTNAME_COLORS = "Colors";
    private static final String GUI_LISTNAME_FONTS = "Fonts";

    private static final String GUI_HOVERTEXT_SWITCHVIEW = "Switch View HTML/Source";
    private static final String GUI_HOVERTEXT_REMOVEFORMAT = "Remove Formatting";
    private static final String GUI_HOVERTEXT_IMAGE = "Insert Image";
    private static final String GUI_HOVERTEXT_HLINE = "Insert Horizontal Line";
    private static final String GUI_HOVERTEXT_BREAKLINK = "Break Link";
    private static final String GUI_HOVERTEXT_LINK = "Generate Link";
    private static final String GUI_HOVERTEXT_IDENTLEFT = "Ident Left";
    private static final String GUI_HOVERTEXT_IDENTRIGHT = "Ident Right";
    private static final String GUI_HOVERTEXT_UNORDERLIST = "Unordered List";
    private static final String GUI_HOVERTEXT_ORDERLIST = "Ordered List";
    private static final String GUI_HOVERTEXT_ALIGNRIGHT = "Align Right";
    private static final String GUI_HOVERTEXT_ALIGNCENTER = "Align Center";
    private static final String GUI_HOVERTEXT_ALIGNLEFT = "Align Left";
    private static final String GUI_HOVERTEXT_SUPERSCRIPT = "Superscript";
    private static final String GUI_HOVERTEXT_SUBSCRIPT = "Subscript";
    private static final String GUI_HOVERTEXT_STROKE = "Stroke";
    private static final String GUI_HOVERTEXT_UNDERLINE = "Underline";
    private static final String GUI_HOVERTEXT_ITALIC = "Italic";
    private static final String GUI_HOVERTEXT_BOLD = "Bold";

    private static final String ICON_BOLD = "/gwt048/images/bold.gif";
    private static final String ICON_ITALIC = "/gwt048/images/italic.gif";
    private static final String ICON_UNDERLINE = "/gwt048/images/underline.gif";
    private static final String ICON_STRIKE_THROUGH = "/gwt048/images/strikeThrough.gif";
    private static final String ICON_SUBSCRIPT = "/gwt048/images/subscript.gif";
    private static final String ICON_SUPERSCRIPT = "/gwt048/images/superscript.gif";
    private static final String ICON_REMOVE_FORMAT = "/gwt048/images/removeFormat.gif";
    private static final String ICON_ALIGN_LEFT = "/gwt048/images/justifyLeft.gif";
    private static final String ICON_ALIGN_CENTER = "/gwt048/images/justifyCenter.gif";
    private static final String ICON_ALIGN_RIGHT = "/gwt048/images/justifyRight.gif";
    private static final String ICON_ORDERED_LIST = "/gwt048/images/ol.gif";
    private static final String ICON_UNORDERED_LIST = "/gwt048/images/ul.gif";
    private static final String ICON_INDENT_RIGHT = "/gwt048/images/indent.gif";
    private static final String ICON_INDENT_LEFT = "/gwt048/images/outdent.gif";
    private static final String ICON_CREATE_LINK = "/gwt048/images/createLink.gif";
    private static final String ICON_REMOVE_LINK = "/gwt048/images/removeLink.gif";
    private static final String ICON_INSERT_IMAGE = "/gwt048/images/insertImage.gif";
    private static final String ICON_HORIZONTAL_LINE = "/gwt048/images/hr.gif";

    private HorizontalPanel toolbarPanel;

    //The RichTextArea this Toolbar referes to and the Interfaces to access the RichTextArea
    private RichTextArea styleText;
    private Formatter styleTextFormatter;

    //We use an internal class of the ClickHandler and the KeyUpHandler to be private to others with these events
    private EventHandler evHandler;

    //The Buttons of the Menubar
    private ToggleButton bold;
    private ToggleButton italic;
    private ToggleButton underline;
    private ToggleButton stroke;
    private ToggleButton subscript;
    private ToggleButton superscript;
    private PushButton alignleft;
    private PushButton alignmiddle;
    private PushButton alignright;
    private PushButton orderlist;
    private PushButton unorderlist;
    private PushButton indentleft;
    private PushButton indentright;
    private PushButton generatelink;
    private PushButton breaklink;
    private PushButton insertline;
    private PushButton insertimage;
    private PushButton removeformatting;
    private ToggleButton texthtml;

    private ListBox fontlist;
    private ListBox colorlist;

    public RichTextToolbar(RichTextArea richtext) {
        //Initialize the two inner panels
        toolbarPanel = new HorizontalPanel();
        toolbarPanel.setStyleName(CSS_ROOT_NAME);

        //Save the reference to the RichText area we refer to and get the interfaces to the stylings
        styleText = richtext;
        styleTextFormatter = styleText.getFormatter();

        //Set some graphical options, so this toolbar looks how we like it.
        toolbarPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_LEFT);

        initWidget(toolbarPanel);

        evHandler = new EventHandler();

        //Add KeyUp and Click-Handler to the RichText, so that we can actualize the toolbar if neccessary
        styleText.addKeyUpHandler(evHandler);
        styleText.addClickHandler(evHandler);

        //Now lets fill the new toolbar with life
        buildTools();
    }

    /**
     * Click Handler of the Toolbar *
     */
    private class EventHandler implements ClickHandler, KeyUpHandler, ChangeHandler {
        public void onClick(ClickEvent event) {
            if (event.getSource().equals(bold)) {
                if (isHTMLMode()) {
                    changeHtmlStyle(HTML_STYLE_OPEN_BOLD, HTML_STYLE_CLOSE_SPAN);
                } else {
                    styleTextFormatter.toggleBold();
                }
            } else if (event.getSource().equals(italic)) {
                if (isHTMLMode()) {
                    changeHtmlStyle(HTML_STYLE_OPEN_ITALIC, HTML_STYLE_CLOSE_SPAN);
                } else {
                    styleTextFormatter.toggleItalic();
                }
            } else if (event.getSource().equals(underline)) {
                if (isHTMLMode()) {
                    changeHtmlStyle(HTML_STYLE_OPEN_UNDERLINE, HTML_STYLE_CLOSE_SPAN);
                } else {
                    styleTextFormatter.toggleUnderline();
                }
            } else if (event.getSource().equals(stroke)) {
                if (isHTMLMode()) {
                    changeHtmlStyle(HTML_STYLE_OPEN_LINETHROUGH, HTML_STYLE_CLOSE_SPAN);
                } else {
                    styleTextFormatter.toggleStrikethrough();
                }
            } else if (event.getSource().equals(subscript)) {
                if (isHTMLMode()) {
                    changeHtmlStyle(HTML_STYLE_OPEN_SUBSCRIPT, HTML_STYLE_CLOSE_SUBSCRIPT);
                } else {
                    styleTextFormatter.toggleSubscript();
                }
            } else if (event.getSource().equals(superscript)) {
                if (isHTMLMode()) {
                    changeHtmlStyle(HTML_STYLE_OPEN_SUPERSCRIPT, HTML_STYLE_CLOSE_SUPERSCRIPT);
                } else {
                    styleTextFormatter.toggleSuperscript();
                }
            } else if (event.getSource().equals(alignleft)) {
                if (isHTMLMode()) {
                    changeHtmlStyle(HTML_STYLE_OPEN_ALIGNLEFT, HTML_STYLE_CLOSE_DIV);
                } else {
                    styleTextFormatter.setJustification(RichTextArea.Justification.LEFT);
                }
            } else if (event.getSource().equals(alignmiddle)) {
                if (isHTMLMode()) {
                    changeHtmlStyle(HTML_STYLE_OPEN_ALIGNCENTER, HTML_STYLE_CLOSE_DIV);
                } else {
                    styleTextFormatter.setJustification(RichTextArea.Justification.CENTER);
                }
            } else if (event.getSource().equals(alignright)) {
                if (isHTMLMode()) {
                    changeHtmlStyle(HTML_STYLE_OPEN_ALIGNRIGHT, HTML_STYLE_CLOSE_DIV);
                } else {
                    styleTextFormatter.setJustification(RichTextArea.Justification.RIGHT);
                }
            } else if (event.getSource().equals(orderlist)) {
                if (isHTMLMode()) {
                    changeHtmlStyle(HTML_STYLE_OPEN_ORDERLIST, HTML_STYLE_CLOSE_ORDERLIST);
                } else {
                    styleTextFormatter.insertOrderedList();
                }
            } else if (event.getSource().equals(unorderlist)) {
                if (isHTMLMode()) {
                    changeHtmlStyle(HTML_STYLE_OPEN_UNORDERLIST, HTML_STYLE_CLOSE_UNORDERLIST);
                } else {
                    styleTextFormatter.insertUnorderedList();
                }
            } else if (event.getSource().equals(indentright)) {
                if (isHTMLMode()) {
                    changeHtmlStyle(HTML_STYLE_OPEN_INDENTRIGHT, HTML_STYLE_CLOSE_DIV);
                } else {
                    styleTextFormatter.rightIndent();
                }
            } else if (event.getSource().equals(indentleft)) {
                if (isHTMLMode()) {
                    //TODO nothing can be done here at the moment
                } else {
                    styleTextFormatter.leftIndent();
                }
            } else if (event.getSource().equals(generatelink)) {
                String url = Window.prompt(GUI_DIALOG_INSERTURL, "http://");
                if (url != null) {
                    if (isHTMLMode()) {
                        changeHtmlStyle("<a href=\"" + url + "\">", "</a>");
                    } else {
                        styleTextFormatter.createLink(url);
                    }
                }
            } else if (event.getSource().equals(breaklink)) {
                if (isHTMLMode()) {
                    //TODO nothing can be done here at the moment
                } else {
                    styleTextFormatter.removeLink();
                }
            } else if (event.getSource().equals(insertimage)) {
                String url = Window.prompt(GUI_DIALOG_IMAGEURL, "http://");
                if (url != null) {
                    if (isHTMLMode()) {
                        changeHtmlStyle("<img src=\"" + url + "\">", "");
                    } else {
                        styleTextFormatter.insertImage(url);
                    }
                }
            } else if (event.getSource().equals(insertline)) {
                if (isHTMLMode()) {
                    changeHtmlStyle(HTML_STYLE_HLINE, "");
                } else {
                    styleTextFormatter.insertHorizontalRule();
                }
            } else if (event.getSource().equals(removeformatting)) {
                if (isHTMLMode()) {
                    //TODO nothing can be done here at the moment
                } else {
                    styleTextFormatter.removeFormat();
                }
            } else if (event.getSource().equals(texthtml)) {
                if (texthtml.isDown()) {
                    styleText.setText(styleText.getHTML());
                } else {
                    styleText.setHTML(styleText.getText());
                }
            } else if (event.getSource().equals(styleText)) {
                //Change invoked by the richtextArea
            }
            updateStatus();
        }

        public void onKeyUp(KeyUpEvent event) {
            updateStatus();
        }

        public void onChange(ChangeEvent event) {
            if (event.getSource().equals(fontlist)) {
                if (isHTMLMode()) {
                    changeHtmlStyle("<span style=\"font-family: " + fontlist.getValue(fontlist.getSelectedIndex()) + ";\">", HTML_STYLE_CLOSE_SPAN);
                } else {
                    styleTextFormatter.setFontName(fontlist.getValue(fontlist.getSelectedIndex()));
                }
            } else if (event.getSource().equals(colorlist)) {
                if (isHTMLMode()) {
                    changeHtmlStyle("<span style=\"color: " + colorlist.getValue(colorlist.getSelectedIndex()) + ";\">", HTML_STYLE_CLOSE_SPAN);
                } else {
                    styleTextFormatter.setForeColor(colorlist.getValue(colorlist.getSelectedIndex()));
                }
            }
        }
    }

    /**
     * Native JavaScript that returns the selected text and position of the start *
     */
    public static native JsArrayString getSelection(Element elem) /*-{
      var txt = "";
      var pos = 0;
      var range;
      var parentElement;
      var container;

      if (elem.contentWindow.getSelection) {
        txt = elem.contentWindow.getSelection();
        pos = elem.contentWindow.getSelection().getRangeAt(0).startOffset;
      } else if (elem.contentWindow.document.getSelection) {
        txt = elem.contentWindow.document.getSelection();
        pos = elem.contentWindow.document.getSelection().getRangeAt(0).startOffset;
      } else if (elem.contentWindow.document.selection) {
        range = elem.contentWindow.document.selection.createRange();
        txt = range.text;
        parentElement = range.parentElement();
        container = range.duplicate();
        container.moveToElementText(parentElement);
        container.setEndPoint('EndToEnd', range);
        pos = container.text.length - range.text.length;
      }
      return ["" + txt, "" + pos];
    }-*/;

    /**
     * Method called to toggle the style in HTML-Mode *
     */
    private void changeHtmlStyle(String startTag, String stopTag) {
        JsArrayString tx = getSelection(styleText.getElement());
        String txbuffer = styleText.getText();
        Integer startpos = Integer.parseInt(tx.get(1));
        String selectedText = tx.get(0);
        styleText.setText(txbuffer.substring(0, startpos) + startTag + selectedText + stopTag + txbuffer.substring(startpos + selectedText.length()));
    }

    /**
     * Private method with a more understandable name to get if HTML mode is on or not *
     */
    private Boolean isHTMLMode() {
        return texthtml.isDown();
    }

    /**
     * Private method to set the toggle buttons and disable/enable buttons which do not work in html-mode *
     */
    private void updateStatus() {
        if (styleTextFormatter != null) {
            bold.setDown(styleTextFormatter.isBold());
            italic.setDown(styleTextFormatter.isItalic());
            underline.setDown(styleTextFormatter.isUnderlined());
            subscript.setDown(styleTextFormatter.isSubscript());
            superscript.setDown(styleTextFormatter.isSuperscript());
            stroke.setDown(styleTextFormatter.isStrikethrough());
        }

        if (isHTMLMode()) {
            removeformatting.setEnabled(false);
            indentleft.setEnabled(false);
            breaklink.setEnabled(false);
        } else {
            removeformatting.setEnabled(true);
            indentleft.setEnabled(true);
            breaklink.setEnabled(true);
        }
    }

    /**
     * Initialize the options on the toolbar *
     */
    private void buildTools() {
        //Init the TOP Panel forst
        toolbarPanel.add(bold = createToggleButton(ICON_BOLD, 20, 20, GUI_HOVERTEXT_BOLD));
        toolbarPanel.add(italic = createToggleButton(ICON_ITALIC, 20, 20, GUI_HOVERTEXT_ITALIC));
        toolbarPanel.add(underline = createToggleButton(ICON_UNDERLINE, 20, 20, GUI_HOVERTEXT_UNDERLINE));
        toolbarPanel.add(stroke = createToggleButton(ICON_STRIKE_THROUGH, 20, 20, GUI_HOVERTEXT_STROKE));
        toolbarPanel.add(new HTML("&nbsp;"));
        toolbarPanel.add(subscript = createToggleButton(ICON_SUBSCRIPT, 20, 20, GUI_HOVERTEXT_SUBSCRIPT));
        toolbarPanel.add(superscript = createToggleButton(ICON_SUPERSCRIPT, 20, 20, GUI_HOVERTEXT_SUPERSCRIPT));
        toolbarPanel.add(new HTML("&nbsp;"));
        toolbarPanel.add(alignleft = createPushButton(ICON_ALIGN_LEFT,   20, 20, GUI_HOVERTEXT_ALIGNLEFT));
        toolbarPanel.add(alignmiddle = createPushButton(ICON_ALIGN_CENTER, 20, 20, GUI_HOVERTEXT_ALIGNCENTER));
        toolbarPanel.add(alignright = createPushButton(ICON_ALIGN_RIGHT, 20, 20, GUI_HOVERTEXT_ALIGNRIGHT));
        toolbarPanel.add(new HTML("&nbsp;"));
        toolbarPanel.add(orderlist = createPushButton(ICON_ORDERED_LIST, 20, 20, GUI_HOVERTEXT_ORDERLIST));
        toolbarPanel.add(unorderlist = createPushButton(ICON_UNORDERED_LIST, 20, 20, GUI_HOVERTEXT_UNORDERLIST));
        toolbarPanel.add(indentright = createPushButton(ICON_INDENT_RIGHT, 20, 20, GUI_HOVERTEXT_IDENTRIGHT));
        toolbarPanel.add(indentleft = createPushButton(ICON_INDENT_LEFT, 20, 20, GUI_HOVERTEXT_IDENTLEFT));
        toolbarPanel.add(new HTML("&nbsp;"));
        toolbarPanel.add(generatelink = createPushButton(ICON_CREATE_LINK, 20, 20, GUI_HOVERTEXT_LINK));
        toolbarPanel.add(breaklink = createPushButton(ICON_REMOVE_LINK, 20, 20, GUI_HOVERTEXT_BREAKLINK));
        toolbarPanel.add(new HTML("&nbsp;"));
        toolbarPanel.add(insertline  = createPushButton(ICON_HORIZONTAL_LINE, 20, 20, GUI_HOVERTEXT_HLINE));
        toolbarPanel.add(insertimage = createPushButton(ICON_INSERT_IMAGE, 20, 20, GUI_HOVERTEXT_IMAGE));
        toolbarPanel.add(new HTML("&nbsp;"));
        toolbarPanel.add(removeformatting = createPushButton(ICON_REMOVE_FORMAT, 20, 20, GUI_HOVERTEXT_REMOVEFORMAT));
        toolbarPanel.add(new HTML("&nbsp;"));
        toolbarPanel.add(texthtml = createToggleButton(ICON_BOLD, 20, 20, GUI_HOVERTEXT_SWITCHVIEW));
        toolbarPanel.add(new HTML("&nbsp;"));

        //Init the BOTTOM Panel
        toolbarPanel.add(fontlist = createFontList());
        toolbarPanel.add(new HTML("&nbsp;"));
        toolbarPanel.add(colorlist = createColorList());
    }

    /**
     * Method to create a Toggle button for the toolbar *
     */
    private ToggleButton createToggleButton(String url, Integer width, Integer height, String tip) {
        Image image = new Image(url);
        ToggleButton tb = new ToggleButton(image);
        tb.setHeight(height + "px");
        tb.setWidth(width + "px");
        tb.addClickHandler(evHandler);
        if (tip != null) {
            tb.setTitle(tip);
        }
        return tb;
    }

    /**
     * Method to create a Push button for the toolbar *
     */
    private PushButton createPushButton(String url, Integer width, Integer height, String tip) {
        Image image = new Image(url);
        PushButton tb = new PushButton(image);
        tb.setHeight(height + "px");
        tb.setWidth(width + "px");
        tb.addClickHandler(evHandler);
        if (tip != null) {
            tb.setTitle(tip);
        }
        return tb;
    }

    /**
     * Method to create the fontlist for the toolbar *
     */
    private ListBox createFontList() {
        ListBox mylistBox = new ListBox();
        mylistBox.addChangeHandler(evHandler);
        mylistBox.setVisibleItemCount(1);

        mylistBox.addItem(GUI_LISTNAME_FONTS);
        for (String name : GUI_FONTLIST.keySet()) {
            mylistBox.addItem(name, GUI_FONTLIST.get(name));
        }

        return mylistBox;
    }

    /**
     * Method to create the colorlist for the toolbar *
     */
    private ListBox createColorList() {
        ListBox mylistBox = new ListBox();
        mylistBox.addChangeHandler(evHandler);
        mylistBox.setVisibleItemCount(1);

        mylistBox.addItem(GUI_LISTNAME_COLORS);
        for (String name : GUI_COLORLIST.keySet()) {
            mylistBox.addItem(name, GUI_COLORLIST.get(name));
        }

        return mylistBox;
    }
}
