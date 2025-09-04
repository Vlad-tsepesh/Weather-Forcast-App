package com.example.weather.infrastructure.output;

import com.example.weather.application.port.TableRenderer;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciitable.CWC_LongestLine;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;

import java.util.List;

public class AsciiTableRenderer implements TableRenderer {

    @Override
    public void render(List<String> headers, List<List<String>> rows) {
        AsciiTable table = new AsciiTable();
        table.addRule();
        table.addRow(headers);
        table.addRule();

        for (List<String> row : rows) {
            table.addRow(row);
            table.addRule();
        }

        table.setTextAlignment(TextAlignment.CENTER);
        table.getRenderer().setCWC(new CWC_LongestLine());
        table.setPaddingLeftRight(1);

        System.out.println(table.render());
    }
}
