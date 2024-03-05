package com.example.demo8;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.*;
import java.util.Comparator;
import java.util.List;

/**
        * HomeController sınıfı, ana ekranın kontrolcüsünü temsil eder.
        * LoginController ve ControllerInterface sınıflarından türetilmiştir.
        * Ayrıca, sol menü kontrolü için LeftMenuInterface'yi uygular.
 */

public class HomeController extends LoginController implements ControllerInterface , LeftMenuInterface {

    // Kullanıcı DAO (Data Access Object) sınıfı
    UserDAO userDAO = new UserDAO();

    // Giriş yapmış kullanıcının ID'sini saklamak için değişken
    int loggedInUserId = userDAO.getLoggedInUserId("john1");


    //FXML Etiketleri:
    @FXML
    private Button addButton;

    @FXML
    private Button adddiseaseButton;

    @FXML
    private Button bloodpressureButton;

    @FXML
    private Button diseaseButton;

    @FXML
    private TableColumn<Disease, String> diseaseColumn;

    @FXML
    private TableView<Disease> diseaseTable;

    @FXML
    private TableColumn<Disease, String> selectedDiseaseColumn;

    @FXML
    private TableView<Disease> selectedDiseaseTable;

    @FXML
    private TableColumn<UserDisease, String> diseasesYouHaveColumn;

    @FXML
    private TableView<UserDisease> diseasesYouHaveTable;

    @FXML
    private Button glucoseButton;

    @FXML
    private Button homeButton;

    @FXML
    private AnchorPane paneDiseases;

    @FXML
    private AnchorPane paneHome;

    @FXML
    private AnchorPane paneGlucose;

    @FXML
    private AnchorPane paneAddDiseases;

    @FXML
    private AnchorPane paneBloodPressure;

    @FXML
    private Button quitButton;

    @FXML
    private Button bloodPressureAddButton;

    @FXML
    private DatePicker bloodPressureDate;

    @FXML
    private TextField bloodPressureTextField;

    @FXML
    private Button glucoseAddButton;

    @FXML
    private DatePicker glucoseDate;

    @FXML
    private TextField glucoseTextField;

    @FXML
    private NumberAxis bloodpressureAxis;

    @FXML
    private CategoryAxis bloodpressureDateAxis;

    @FXML
    private LineChart<String, Number> bloodpressureLineChart;

    @FXML
    private NumberAxis glocoseAxis;

    @FXML
    private CategoryAxis glucoseDateAxis;

    @FXML
    private LineChart<String, Number> glucoseLineChart;


    private ActionEvent storedevent1;


    private int menu = 0;





    private ObservableList<Disease> allDiseases = FXCollections.observableArrayList();
    private ObservableList<Disease> selectedDiseases = FXCollections.observableArrayList();


    /**
     * Ana ekranın başlangıç ayarlamalarını yapar.
     */
    public void initialize() {

        diseaseColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        selectedDiseaseColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        diseasesYouHaveColumn.setCellValueFactory(new PropertyValueFactory<>("diseaseName"));

        menuControl();


        ObservableList<Disease> databaseDiseases = getDiseasesFromDatabase();
        allDiseases.addAll(databaseDiseases);
        System.out.println("Database Diseases: "+databaseDiseases);

        diseaseTable.setItems(allDiseases);
        System.out.println("All Diseases: " + allDiseases);

        diseaseTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedDiseases.add(newValue);
                System.out.println("Selected Disease: " + newValue.getName());
                selectedDiseaseTable.refresh();
            }

            System.out.println("Initialized!!");
        });
        selectedDiseaseTable.setItems(selectedDiseases);

        ObservableList<UserDisease> userDiseases = getUserDiseasesFromDatabase();
        diseasesYouHaveTable.setItems(userDiseases);
        refreshUserDiseasesTable();

        initializeBloodPressureChart();
        initializeGlucoseChart();

    }

    /**
     * Tansiyon grafiğini başlatır.
     */private void initializeBloodPressureChart() {

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        ObservableList<BloodPressureData> bloodPressureData = getBloodPressureDataFromDatabase();

        for (BloodPressureData data : bloodPressureData) {
            series.getData().add(new XYChart.Data<>(data.getBloodPressureMeasurementDate(), data.getBloodPressureValue()));
        }

        bloodpressureLineChart.getData().add(series);
    }


    /**
     * Şeker grafiğini başlatır.
     */
    private void initializeGlucoseChart(){
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        ObservableList<GlucoseData> glucoseData = getGlucoseDataFromDatabase();

        for (GlucoseData data : glucoseData){
            series.getData().add(new XYChart.Data<>(data.getGlucoseMeasurementDate(), data.getGlucoseValue()));
        }

        glucoseLineChart.getData().add(series);
    }


    /**
     *Add butonuna basıldığında seçilen hastalığın diseasesYouHaveTable'a yazılmasını sağlar.
     */
    @FXML
    private void addButtonOnAction(ActionEvent e) {
        System.out.println("add button clicked!!");
        addSelectedDiseasesToUserTable(selectedDiseases);
        selectedDiseaseTable.setItems(selectedDiseases);
        System.out.println("Selected Diseases: " + selectedDiseases);

        ObservableList<UserDisease> userDiseases = getUserDiseasesFromDatabase();
        diseasesYouHaveTable.setItems(userDiseases);
    }

    /**
     *Delete butonuna basıldığında seçilen hastalığın diseasesYouHaveTable'dan silinmesini sağlar.
     */@FXML
    private void deleteButtonOnAction(ActionEvent e){
        System.out.println("delete button clicked");

        deleteSelectedDiseases(selectedDiseases);
        selectedDiseaseTable.setItems(selectedDiseases);
        System.out.println("Selected Diseases: " + selectedDiseases);

        ObservableList<UserDisease> userDiseases = getUserDiseasesFromDatabase();
        diseasesYouHaveTable.setItems(userDiseases);

    }





    /**
     * Veritabanından hastalık verilerini getirir.
     *
     * @return Hastalık listesi
     */
    private ObservableList<Disease> getDiseasesFromDatabase() {
        ObservableList<Disease> diseases = FXCollections.observableArrayList();

        try (Connection connection = new DatabaseConnection().getConnection()) {
            String query = "SELECT * FROM diseasetrack.diseases";
            try (Statement statement = connection.createStatement()) {  //sql sorguus için
                try (ResultSet resultSet = statement.executeQuery(query)) {     //sql sorgusu sonucundaki dönen veri kümesi
                    while (resultSet.next()) {
                        int id = resultSet.getInt("diseaseid");
                        String name = resultSet.getString("diseasename");

                        diseases.add(new Disease(id, name));
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return diseases;
    }


    /**
     * Veritabanından kişinin sahip olduğu hastalıkların verilerini getirir.
     *
     * @return UserDiseases listesi
     */
    private ObservableList<UserDisease> getUserDiseasesFromDatabase() {
        ObservableList<UserDisease> userDiseases = FXCollections.observableArrayList();


        try (Connection connection = new DatabaseConnection().getConnection()) {

            String query = "SELECT d.diseasename FROM diseasetrack.userdiseases ud " +
                    "JOIN diseasetrack.diseases d ON ud.diseaseid = d.diseaseid " +
                    "WHERE ud.userid = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, loggedInUserId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String diseaseName = resultSet.getString("diseasename");
                        userDiseases.add(new UserDisease(diseaseName));

                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return userDiseases;
    }





    /**
     * Seçilen hastalıkları kullanıcı tablosuna ekler.
     *
     * @param selectedDiseases Seçilen hastalıkların listesi
     */
    private void addSelectedDiseasesToUserTable(List<Disease> selectedDiseases) {
        int userId = getLoggedInUserId();
        System.out.println("User ID: " + userId);

        if (userId == 0) {
            System.out.println("User not found.");
            return;
        }

        try (Connection connection = new DatabaseConnection().getConnection()) {
            String query = "INSERT INTO diseasetrack.userdiseases (userid, diseaseid) VALUES (?, ?)";
            System.out.println("SQL Query: " + query);
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                for (Disease disease : selectedDiseases) {
                    int diseaseId = disease.getId();
                    System.out.println("Disease ID: " + diseaseId);

                    if (!isDiseaseAlreadyAdded(userId, diseaseId)) {
                        preparedStatement.setInt(1, userId);
                        preparedStatement.setInt(2, diseaseId);
                        preparedStatement.executeUpdate();

                        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                            if (generatedKeys.next()) {
                                int userDiseaseId = generatedKeys.getInt(1);


                                Disease userDisease = new Disease(userDiseaseId, disease.getName());
                                selectedDiseases.add(userDisease);
                            }
                        }


                        System.out.println("Added Disease to User: " + disease.getName());
                    } else {
                        System.out.println("Disease already added for the user: " + disease.getName());
                    }


                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }



    /**
     * Seçilen hastalıkları kullanıcı tablosundan siler.
     *
     * @param selectedDiseases Silinecek hastalıkların listesi
     */
    private void deleteSelectedDiseases(ObservableList<Disease> selectedDiseases) {
        try (Connection connection = new DatabaseConnection().getConnection()) {
            String query = "DELETE FROM diseasetrack.userdiseases WHERE userid = ? AND diseaseid = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                for (Disease disease : selectedDiseases) {
                    int userId = getLoggedInUserId();
                    int diseaseId = disease.getId();

                    preparedStatement.setInt(1, userId);
                    preparedStatement.setInt(2, diseaseId);

                    preparedStatement.executeUpdate();

                    System.out.println("Deleted Disease from User: " + disease.getName());
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }



    /**
     * Veritabanında daha önce belirtilen hastalığın kullanıcı tarafından eklenip eklenmediğini kontrol eder.
     *
     * @param userId    Kullanıcı ID'si
     * @param diseaseId Hastalık ID'si
     * @return true, hastalık zaten eklenmişse; false, eklenmemişse
     */
    private boolean isDiseaseAlreadyAdded(int userId, int diseaseId) {

        try (Connection connection = new DatabaseConnection().getConnection()) {
            String query = "SELECT COUNT(*) FROM diseasetrack.userdiseases WHERE userid = ? AND diseaseid = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, userId);
                preparedStatement.setInt(2, diseaseId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        return count > 0;
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }


    /**
     * Giriş yapmış kullanıcının ID'sini döndürür.
     *
     * @return Kullanıcı ID'si
     */
    private int getLoggedInUserId() {
        return loggedInUserId;
    }



    /**
     * Kan basıncı verilerini veritabanından getirir.
     *
     * @return Kan basıncı veri listesi
     */
    private ObservableList<BloodPressureData> getBloodPressureDataFromDatabase() {
        ObservableList<BloodPressureData> bloodPressureData = FXCollections.observableArrayList();

        try (Connection connection = new DatabaseConnection().getConnection()) {
            String query = "SELECT * FROM bloodpressure WHERE userid = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, loggedInUserId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String measurementDate = resultSet.getString("measurement_date");
                        int bloodPressureValue = resultSet.getInt("blood_pressure");

                        bloodPressureData.add(new BloodPressureData(measurementDate, bloodPressureValue));
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return bloodPressureData;
    }


    /**
     * Kan basıncı verilerini ekler. Bu metod, kullanıcının girdiği kan basıncı verilerini
     * veritabanına ekler ve aynı zamanda kan basıncı grafiğini günceller.
     *
     * @param event Kullanıcının eylemi
     */
    @FXML
    private void addBloodPressureData(ActionEvent event) {

        // Kullanıcının girdiği kan basıncı ölçüm tarihini ve değerini alır
        String bloodPressureMeasurementDate = bloodPressureDate.getValue().toString();
        int bloodPressureValue = Integer.parseInt(bloodPressureTextField.getText());

        // Veritabanına kan basıncı verisini ekler
        insertBloodPressureData(loggedInUserId, bloodPressureMeasurementDate, bloodPressureValue);

        // Kan basıncı grafiğini günceller
        ObservableList<XYChart.Data<String, Number>> chartData = bloodpressureLineChart.getData().get(0).getData();
        chartData.add(new XYChart.Data<>(bloodPressureMeasurementDate, bloodPressureValue));
        chartData.sort(Comparator.comparing(XYChart.Data::getXValue));

        // Kullanıcının girdiği alanları temizler
        bloodPressureDate.setValue(null);
        bloodPressureTextField.clear();


        // Yeni veriyi içeren bir seri oluşturup grafiği günceller
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().addAll(chartData);
        bloodpressureLineChart.getData().add(series);


    }




    /**
     * Kan basıncı verisini veritabanına ekler. Bu metod, kullanıcının girdiği kan basıncı ölçüm
     * tarihini, kan basıncı değerini ve kullanıcı kimliğini alır, veritabanına ekler ve eklenen
     * verinin kimlik numarasını döndürür.
     *
     * @param userId Kullanıcının kimliği
     * @param bloodPressureMeasurementDate Kan basıncı ölçüm tarihi
     * @param bloodPressureValue Kan basıncı değeri
     * @return Eklenen verinin kimlik numarası
     */
    private int insertBloodPressureData(int userId, String bloodPressureMeasurementDate, int bloodPressureValue) {
        try (Connection connection = new DatabaseConnection().getConnection()) {
            String query = "INSERT INTO bloodpressure (userid, measurement_date, blood_pressure) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setInt(1, userId);
                preparedStatement.setString(2, bloodPressureMeasurementDate);
                preparedStatement.setInt(3, bloodPressureValue);
                preparedStatement.executeUpdate();

                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int bloodPressureId = generatedKeys.getInt(1);
                        return bloodPressureId;
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1; // Eğer bir hata olursa -1 döndürür
    }

    /**
     * Veritabanından kullanıcının şeker verilerini alır. Bu metod, kullanıcının
     * kimliği ile eşleşen şeker verilerini çeker ve bir ObservableList'e ekler.
     *
     * @return Kullanıcının şeker verilerini içeren ObservableList
     */
    private ObservableList<GlucoseData> getGlucoseDataFromDatabase() {
        ObservableList<GlucoseData> glucoseData = FXCollections.observableArrayList();

        try (Connection connection = new DatabaseConnection().getConnection()) {
            String query = "SELECT * FROM glucose WHERE userid = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, loggedInUserId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String glucoseMeasurementDate = resultSet.getString("glucose_measurement_date");
                        int glucoseValue = resultSet.getInt("glucose_level");

                        glucoseData.add(new GlucoseData(glucoseMeasurementDate, glucoseValue));
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return glucoseData;
    }







    /**
     * Şeker verisini veritabanına ekler. Bu metod, kullanıcının girdiği şeker ölçüm
     * tarihini, şeker değerini ve kullanıcı kimliğini alır, veritabanına ekler ve eklenen
     * verinin kimlik numarasını döndürür.
     *
     * @param userId Kullanıcının kimliği
     * @param glucoseMeasurementDate Şeker ölçüm tarihi
     * @param glucoseValue Şeker değeri
     * @return Eklenen verinin kimlik numarası
     */
    private int insertGlucoseData(int userId, String glucoseMeasurementDate, int glucoseValue) {

        try (Connection connection = new DatabaseConnection().getConnection()) {
            String query = "INSERT INTO glucose (userid, glucose_measurement_date, glucose_level) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setInt(1, userId);
                preparedStatement.setString(2, glucoseMeasurementDate);
                preparedStatement.setInt(3, glucoseValue);
                preparedStatement.executeUpdate();

                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int glucoseId = generatedKeys.getInt(1);

                        return glucoseId;
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1; // Eğer bir hata olursa -1 döndürür
    }



    /**
     * Şeker verilerini ekler. Bu metod, kullanıcının girdiği şeker verilerini
     * veritabanına ekler ve aynı zamanda şeker grafiğini günceller.
     *
     * @param event Kullanıcının eylemi
     */
    @FXML
    private void addGlucoseData(ActionEvent event) {
        // Kullanıcının girdiği şeker ölçüm tarihini ve değerini alır
        String glucoseMeasurementDate = glucoseDate.getValue().toString();
        int glucoseValue = Integer.parseInt(glucoseTextField.getText());

        // Veritabanına şeker verisini ekler
        insertGlucoseData(loggedInUserId, glucoseMeasurementDate, glucoseValue);

        // Şeker grafiğini günceller
        ObservableList<XYChart.Data<String, Number>> chartData = glucoseLineChart.getData().get(0).getData();
        chartData.add(new XYChart.Data<>(glucoseMeasurementDate, glucoseValue));
        chartData.sort(Comparator.comparing(XYChart.Data::getXValue));

        // Kullanıcının girdiği alanları temizler
        glucoseDate.setValue(null);
        glucoseTextField.clear();

        // Yeni veriyi içeren bir seri oluşturup grafiği günceller
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().addAll(chartData);
        bloodpressureLineChart.getData().add(series);
    }



    /**
     * İlgili tabloları yenileyen metodlar.
     */
    private void refreshUserDiseasesTable() {
        ObservableList<UserDisease> userDiseases = getUserDiseasesFromDatabase();
        diseasesYouHaveTable.setItems(userDiseases);
    }

    @FXML
    private void refreshSelectedDiseaseTable(ActionEvent e){
        System.out.println("refresh button clicked");
        refreshDiseasesTable();
        selectedDiseaseTable.getItems().removeAll(selectedDiseases);

    }

    private void refreshDiseasesTable() {
        ObservableList<Disease> Disease = getDiseasesFromDatabase();
        diseaseTable.setItems(Disease);
    }


    @FXML
    /**
     * Sol menüdeki ilgili butonlara basıldığında pane değiştirilmesini sağlar.
     */
    public void quitButtonOnAction(ActionEvent e) throws IOException {
        storedevent1=e;
        switchToScene1(storedevent1);
    }


    @FXML
    public void homeButtonClick(MouseEvent event)
    {
        menuControl();
        paneHome.setVisible(true);
        menu = 1;

    }

    @FXML
    public void diseasesClick(MouseEvent event)
    {
        menuControl();
        paneDiseases.setVisible(true);
        menu = 2;
    }

    @FXML
    public void glucoseClick(MouseEvent event)
    {
        menuControl();
        paneGlucose.setVisible(true);
        menu = 3;
    }

    @FXML
    public void bloodPressureClick(MouseEvent event)
    {
        menuControl();
        paneBloodPressure.setVisible(true);
        menu = 4;
    }

    @FXML
    public void addDiseasesClick(MouseEvent event)
    {
        menuControl();
        paneAddDiseases.setVisible(true);
        menu = 5;
    }






    /**
     * Menü kontrolü yapar ve ilgili pane'i görünür kılar.
     */
    public void menuControl() {
        switch (menu) {
            case 0:
                paneHome.setVisible(false);
                paneDiseases.setVisible(false);
                paneAddDiseases.setVisible(false);
                paneBloodPressure.setVisible(false);
                paneGlucose.setVisible(false);
                break;
            case 1:
                paneHome.setVisible(false);
                break;
            case 2:
                paneDiseases.setVisible(false);
                break;
            case 3:
                paneGlucose.setVisible(false);
                break;
            case 4:
                paneBloodPressure.setVisible(false);
                break;
            case 5:
                paneAddDiseases.setVisible(false);
                break;
            default:
                break;
        }
    }




}
