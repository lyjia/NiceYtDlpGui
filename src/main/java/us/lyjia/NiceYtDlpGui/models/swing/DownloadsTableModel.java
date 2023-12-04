package us.lyjia.NiceYtDlpGui.models.swing;

import us.lyjia.NiceYtDlpGui.Tweakables;
import us.lyjia.NiceYtDlpGui.models.Download;
import us.lyjia.NiceYtDlpGui.models.YtDlp;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DownloadsTableModel extends AbstractTableModel {
  
  //might be useful later
  //https://tips4java.wordpress.com/2009/07/12/table-button-column/
  
  ArrayList<Download> downloadPile;
  String[] keys;
  ArrayList<String> columnNames = new ArrayList<String>(Tweakables.initDownloadProgressColumns().values());
  
  public DownloadsTableModel(ArrayList<Download> downloads) {
    downloadPile = downloads;
    keys = YtDlp.getProgressTemplateKeys();
  }
  
  @Override
  public int getRowCount() {
    return downloadPile.size();
  }
  
  @Override
  public int getColumnCount() {
    return keys.length;
  }
  
  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    return downloadPile.get(rowIndex).getProgressStat( keys[columnIndex] );
  }
  
  @Override
  public String getColumnName(int column) {
    return columnNames.get(column);
  }
}
