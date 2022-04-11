import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class SortedListGUIRunner extends JFrame
{
    JPanel mainPnl;
    JPanel displayPnl;

    JPanel orderedListPnl;
    JTextArea orderedListTA;
    JScrollPane orderedListScroller;

    JPanel searchedListPnl;
    JTextArea searchedListTA;
    JScrollPane searchListScroller;

    JPanel controlPnl;
    JButton addListEntryBtn;
    JButton searchListBtn;
    JButton clearBtn;

    ArrayList<String> orderedList = new ArrayList<>();

    public SortedListGUIRunner()
    {
        mainPnl = new JPanel();
        mainPnl.setLayout(new GridLayout(2,1));

        createDisplayPnl();
        mainPnl.add(displayPnl);

        createControlPnl();
        mainPnl.add(controlPnl);

        add(mainPnl);
        setSize(900,700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void createDisplayPnl()
    {
        displayPnl = new JPanel();
        displayPnl.setLayout(new GridLayout(1,2));

        //add orderedListTA
        createOrderedListPnl();
        displayPnl.add(orderedListPnl, BorderLayout.WEST);

        //add searchListTA
        createSearchListPnl();
        displayPnl.add(searchedListPnl, BorderLayout.EAST);
    }

    private void createOrderedListPnl()
    {
        orderedListPnl = new JPanel();
        orderedListPnl.setBorder(new TitledBorder(new EtchedBorder(), "Ordered List"));
        orderedListTA = new JTextArea(17,60);
        orderedListTA.setFont(new Font("monospaced", Font.PLAIN, 12));
        orderedListScroller = new JScrollPane(orderedListTA);

        orderedListPnl.add(orderedListScroller);
    }

    private void createSearchListPnl()
    {
        searchedListPnl = new JPanel();
        searchedListPnl.setBorder(new TitledBorder(new EtchedBorder(), "Searched List"));
        searchedListTA = new JTextArea(17, 60);
        searchedListTA.setFont(new Font("monospaced", Font.PLAIN, 12));
        searchListScroller = new JScrollPane(searchedListTA);

        searchedListPnl.add(searchListScroller);
    }

    private void createControlPnl()
    {
        controlPnl = new JPanel();
        controlPnl.setBorder(new TitledBorder(new EtchedBorder(), "Controls"));

        addListEntryBtn = new JButton("Add List Entry");
        addListEntryBtn.addActionListener((ActiveEvent_ae) ->
        {
            boolean checkForFirstItem = false;

            String entry = getNonZeroLenString("Enter a word to the list").toLowerCase(Locale.ROOT);
            //clears list before each entry
            orderedListTA.setText("");

            //if new entry isn't already in the list then add it
            if(!orderedList.contains(entry)) {
                orderedList.add(entry);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "The word you want to add is already in the list");
            }

            //sort list then display each word on a separate line
            Collections.sort(orderedList);
            for (String s: orderedList)
            {
                if (checkForFirstItem) {
                    orderedListTA.append("\n" + s);
                }
                else
                {
                    orderedListTA.append(s);
                    checkForFirstItem = true;
                }
            }
        });
        controlPnl.add(addListEntryBtn);

        searchListBtn = new JButton("Search List");
        searchListBtn.addActionListener((ActiveEvent_ae) ->
        {
            String searchedWord = getNonZeroLenString("Please enter the word you're looking for");

            //search list for index of word using binarySearch method
            int indexSearchResult = binarySearch(orderedList, searchedWord);

            if(indexSearchResult != -1)
            {
                searchedListTA.append("\nThe word:" + searchedWord + " was found at index:" + indexSearchResult);
            }
            else
            {
                searchedListTA.append("\nSorry. The word you are looking for is not in the list.");
            }
        });
        controlPnl.add(searchListBtn);

        
        clearBtn = new JButton("Clear Panels");
        clearBtn.addActionListener((ActiveEvent_ae) ->
        {
            orderedList.clear();
            orderedListTA.setText("");
            searchedListTA.setText("");
        });
        controlPnl.add(clearBtn);
    }

    private int binarySearch(ArrayList<String> array, String x)
    {
        int a = 0;
        int b = array.size() - 1;

        while(a <= b)
        {
            int c = a + (b-a) / 2;

            int result = x.compareTo(array.get(c));

            //check if x is in the mid of list
            if(result == 0)
            {
                return c;
            }
            //if x is >, ignore the first half list
            else if(result > 0)
            {
                a = c + 1;
            }
            //if x is <, ignore second half of list
            else
            {
                b = c - 1;
            }
        }

        return -1;
    }

    private String getNonZeroLenString(String prompt)
    {
        String retString = JOptionPane.showInputDialog(prompt + ": ");
        do
        {
            if(retString.length() == 0) {
                JOptionPane.showMessageDialog(null, "Please enter something.");
                retString = JOptionPane.showInputDialog(prompt + ": ");
            }
        }while(retString.length() == 0); // until we have some characters

        return retString;

    }
}
