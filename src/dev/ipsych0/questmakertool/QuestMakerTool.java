package dev.ipsych0.questmakertool;

import com.google.gson.Gson;
import dev.ipsych0.myrinnia.quests.QuestVO;
import dev.ipsych0.myrinnia.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class QuestMakerTool extends JFrame {
    private JButton createButton;
    private JTextField questNameText;
    private JTextField objectivesText;
    private JTextField questStartText;
    private JButton addButton;
    private JTextArea jsonText;
    private JPanel mainPanel;

    private QuestVO questVO;
    private String questName;
    private String questStart;
    private List<String> objectives = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        QuestMakerTool questMakerTool = new QuestMakerTool();
    }

    public QuestMakerTool() throws Exception {
        setSize(new Dimension(400, 600));
        setLocationRelativeTo(null);
        add(mainPanel);
        setTitle("Quest Maker Tool");

        setVisible(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());

        initListeners();
    }

    private void initListeners() {
        addButton.addActionListener((l) -> {
            if (!objectivesText.getText().isEmpty() && !questNameText.getText().isEmpty() && !questStartText.getText().isEmpty()) {
                this.questName = questNameText.getText();
                this.questStart = questStartText.getText();

                objectives.add(objectivesText.getText());
                objectivesText.setText("");

                questVO = new QuestVO(questName, questStart, objectives);

                jsonText.setText(Utils.getGson().toJson(questVO));
            } else {
                System.err.println("Objectives is empty.");
            }
        });

        createButton.addActionListener((l) -> {
            if (!jsonText.getText().isEmpty()) {
                try {
                    Gson gson = Utils.getGson();
                    QuestVO questVO = gson.fromJson(jsonText.getText(), QuestVO.class);
                    Files.write(Paths.get("src/dev/ipsych0/myrinnia/quests/json/" + questVO.getQuestName().replaceAll(" ", "").toLowerCase() + ".json"), jsonText.getText().getBytes());

                    questStartText.setText("");
                    questNameText.setText("");
                    objectivesText.setText("");
                    objectives.clear();
                    questVO = new QuestVO();
                } catch (Exception e) {
                    System.err.println("JSON parsing error:\n");
                    e.printStackTrace();
                }
            }
        });
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(10, 1, new Insets(0, 0, 0, 0), -1, -1));
        createButton = new JButton();
        createButton.setText("Create");
        mainPanel.add(createButton, new com.intellij.uiDesigner.core.GridConstraints(8, 0, 2, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        questNameText = new JTextField();
        mainPanel.add(questNameText, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        objectivesText = new JTextField();
        mainPanel.add(objectivesText, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        questStartText = new JTextField();
        mainPanel.add(questStartText, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        addButton = new JButton();
        addButton.setText("Add");
        mainPanel.add(addButton, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jsonText = new JTextArea();
        jsonText.setRows(0);
        mainPanel.add(jsonText, new com.intellij.uiDesigner.core.GridConstraints(7, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Quest Name:");
        mainPanel.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Quest Start:");
        mainPanel.add(label2, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Objective:");
        mainPanel.add(label3, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }

}