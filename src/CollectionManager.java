import Interfaces.Manual;
import models.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class CollectionManager {

    /** HashSet collection for keeping a collection as java-object */
    private LinkedList<Product> products;
    /** Field used for saving collection into xml file */
    private File xmlfile;
    /** Field for saving date of initialization thw collection */
    private ZonedDateTime initializationDate;
    /** Field for keeping the manual container */
    private Manual manual;
    /** Field for checking the program was started */
    private boolean wasStart;
    /** Container for storing used commands */
    private UsedCommandsContainer usedCommandsContainer;

    {
        wasStart = false;
        var value = new Product();
        products = new LinkedList<>();
    }

    // Constructor for checking a path to file existence and file readiness to work
    public CollectionManager(Manual manual, UsedCommandsContainer usedCommandsContainer) {
        this.manual = manual;
        this.usedCommandsContainer = usedCommandsContainer;
        init();
    }

    public void reorder(){
        Collections.reverse(products);
    }

    public UsedCommandsContainer getUsedCommandsContainer(){
        return usedCommandsContainer;
    }
    public void init(){
        Scanner scanner = new Scanner(System.in);
        try {
            for ( ; ; ) {
                System.out.print("Enter a full path to XML file with collection or of the file where collection elements are " +
                        "going to be stored to while being saved: ");
                var pathToFile = scanner.nextLine();
                if (!checkPermissions(pathToFile)){
                    System.out.println("You dont have access to the specified file. Try again.");
                    init();
                    return;
                }
                try {
                    wasStart = true;
                    initializationDate = ZonedDateTime.now();
                    xmlfile = new File(pathToFile);
                    if(!xmlfile.exists()){
                        System.out.println("0 products were downloaded");
                        return;
                    }
                    final QName qName = new QName("product");
                    // create xml event reader for input stream
                    XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
                    xmlfile = new File(pathToFile);
                    var fileReader = new FileReader(xmlfile);
                    XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(fileReader);
                    // initialize jaxb
                    JAXBContext context = JAXBContext.newInstance(Products.class);
                    Unmarshaller unmarshaller = context.createUnmarshaller();
                    XMLEvent e;
                    products = unmarshaller.unmarshal(xmlEventReader, Products.class).getValue().getProducts();
                    Collections.sort(products);
                    System.out.println("loaded " + " products: " + products.size());
                    break;
                } catch (JAXBException jaxbException) {
                    System.out.println("the file is empty or XML syntax error.");
                } catch (FileNotFoundException fileNotFoundException) {
                    System.out.println("File not found");
                } catch (XMLStreamException xmlStreamException) {
                    System.out.println("XML Stream error");
                }
            }
        } catch (NoSuchElementException noSuchElementException) {
            System.out.println("Program will be finished now.");
            System.exit(0);
        }
    }

    public void execute_script(String nameOfFile){
        try {
            System.out.println("WARNING. To avoid recursion, your file cannot contain execute script commands.");
            BufferedReader reader = new BufferedReader(new FileReader(nameOfFile));
            String[] finalUserCommand;
            String command;
            while((command = reader.readLine()) != null) {
                finalUserCommand = command.trim().toLowerCase().split(" ", 2);
                switch (finalUserCommand[0]) {
                    case "":
                        break;
                    case "help":
                        help();
                        break;
                    case "info":
                        info();
                        break;
                    case "show":
                        show();
                        break;
                    case "add":
                        add();
                        break;
                    case "update_by_id":
                        update_by_id(finalUserCommand[1]);
                        break;
                    case "remove_by_id":
                        remove_by_id(finalUserCommand[1]);
                        break;
                    case "clear":
                        clear();
                        break;
                    case "save":
                        save();
                        break;
                    case "execute_script":
                        execute_script(finalUserCommand[1]);
                        break;
                    case "exit":
                        exit();
                        break;
                    case "print_unique_unit_of_measure":
                        print_unique_unit_of_measure();
                        break;
                    case "filter_greater_than_price":
                        filter_greater_than_price(finalUserCommand[1]);
                        break;
                    case "remove_first":
                        remove_first();
                        break;
                    case "reorder":
                        reorder();
                        break;
                    case "history":
                        history();
                        break;
                    default:
                        reader.readLine();
                        break;
                }
                if(!finalUserCommand[0].isEmpty())
                    System.out.println("Command ended.");
            }
            System.out.println("Commands ended.");
            reader.close();
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("File not found. Try again.");
        } catch (IOException ioException) {
            System.out.println("File reading exception. Try again.");
        }
    }

    public void add(){
                    System.out.println("adding product..");
                    var maxId = Long.MIN_VALUE;
                    for (var product : products) {
                        maxId = Long.max(maxId, product.getId());
                    }
                    var id = products.size() == 0 ? 1 : maxId + 1;
                    var name = inputName();
                    var coord = inputCoordinates();
                    var price = inputPrice();
                    var manufCost = inputManufactureCost();
                    var unit = inputUnitOfMeasure();

                    int yesOrNo = 0;
                    for( ; ; ) {
                        try {
                            System.out.println("enter a number: ");
                            System.out.println("should we add organization? enter the number: 1 - Yes or 2 - No");
                            Scanner scanner = new Scanner(System.in);
                            yesOrNo = scanner.nextInt();
                            if (yesOrNo == 1 || yesOrNo == 2)
                                if(yesOrNo == 2)
                                    System.out.println("organization will not be defined");
                                break;
                        } catch (InputMismatchException e) {
                            System.out.println("enter a number: ");
                        }
                    }
                    var prod = new Product(id, name, coord, price, manufCost,
                                unit, yesOrNo == 1 ? inputOrganization() : null);
                    if (products.size() == 0) {
                        products.add(prod);
                        return;
                    }
                    if (products.peekLast().getId() == maxId)
                        products.add(prod);
                    else
                        products.addFirst(prod);

    }

    public void clear(){
        products.clear();
    }

    public void remove_first(){
        products.removeFirst();
    }

    public void remove(long id){
        products.remove(id);
    }

    public boolean validateData(){
        if(products.isEmpty())
            return true;

        var organizations = new LinkedList<Organization>();
        var productIds = new HashSet<Long>();
        var organizationIds = new HashSet<Long>();

        for (var prod: products
             ) {
            if(prod.getManufacturer() !=  null){
                var organization = prod.getManufacturer();
                organizationIds.add(organization.getId());
                organizations.add(prod.getManufacturer());
                if(organization.getName() == null || organization.getName().isEmpty() || organization.getAnnualTurnover() == null ||
                        organization.getAnnualTurnover() < 1 || organization.getOrganizationType() == null){
                    return false;
                }
            }
            if(prod.getPrice() < 1 || prod.getCreationDate() == null || prod.getCoordinates() == null ||
            prod.getName() == null || prod.getManufactureCost() == null || prod.getCoordinates().getX() == null)
                return false;

            productIds.add(prod.getId());
        }
        var minProductId = productIds.stream().reduce(Long.MAX_VALUE, (m,i)-> {if (i< m){m = i;} return m; });
        var minOrganizationId = organizationIds.stream().reduce(Long.MAX_VALUE, (m,i)-> {if (i< m){m = i;} return m; });

        if(productIds.stream().count() < products.size() || minProductId < 1
                || minOrganizationId < 1 || organizationIds.stream().count() < organizationIds.size()){
            return false;
        }
        return true;

    }

    /** Method for printing information about the collection */
    public void info() {
        System.out.println("Type of collection: " + products.getClass());
        System.out.println("Initialization date: " + initializationDate.format(DateTimeFormatter.ofPattern("dd/MM/YYYY HH:MM:ss")));
        System.out.println("Amount of elements in the collection: " + products.size());
        System.out.println("Collection manager is active: " + wasStart);
    }

    /**
     * Class which check file is existed, and can be readable and writeable.
     * @return result of checkup
     */
    public boolean checkPermissions(String pathToFile) {
        File checkingFile = new File(pathToFile);
        if (!checkingFile.exists()) {
            return true;
        }
        if (!checkingFile.canRead()) {
            System.out.println("File cannot be readable. You should to have this permission.");
            return false;
        }
        if (!checkingFile.canWrite()) {
            System.out.println("File cannot be writeable. You should to have this permission.");
            return false;
        }
        return true;
    }

    /** Method for saving (marshaling) java collection to XML-file and updating hash of file */
    public boolean save() {
        try {
            if(products.size() == 0)
                return true;
            var fileWriter = new FileWriter(xmlfile);
            if(!validateData())
                return false;
            var productsXml = new Products();
            productsXml.setProducts(products);
            JAXBContext jaxbContext = JAXBContext.newInstance(Products.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            //Marshal the persons list in file
            jaxbMarshaller.marshal(productsXml, fileWriter);
            return true;
        } catch (JAXBException jaxbException) {
            System.out.println("XML syntax error. Try again. ");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
         return true;
    }

    /** Method for printing collection elements into the string representation */
    public void show() {
        for (Product product : products) {
            System.out.println(product.toString() + "\n");
        }
        if(products.size() == 0)
            System.out.println("there is no products yet.. add a new one");

    }

    public Coordinates inputCoordinates(){
        System.out.println("adding coordinates..");
        var coor = new Coordinates(inputXLocation(), inputYLocation());
        System.out.println("done with coordinates..");
        return coor;
    }

    public float inputPrice(){
        for ( ; ; ) {
            try {
                System.out.print("Enter the price of the product: ");
                Scanner scanner = new Scanner(System.in);
                return scanner.nextFloat();
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("This value must be a float-type number. Try again. ");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully. ");
                System.exit(1);
            }
        }
    }

    public Double inputManufactureCost(){
        for ( ; ; ) {
            try {
                System.out.print("Enter manufacture cost: ");
                Scanner scanner = new Scanner(System.in);
                return scanner.nextDouble();
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("This value must be a Double-type number. Try again. ");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully. ");
                System.exit(1);
            }
        }
    }

    /** Method for switching off the program */
    public void exit() {
        try {
            System.out.println("Program will be finished now. ");
            TimeUnit.SECONDS.sleep(3);
            System.exit(0);
        } catch (InterruptedException interruptedException) {
            System.out.println("Program will be finished now.");
            System.exit(0);
        }
    }

    /** Method for printing manual for user */
    public void help() {
        for (Map.Entry<String, String> entry : manual.getCommandInfo().entrySet()) {
            System.out.println(entry.getKey() + entry.getValue());
        }
    }

    /** Method for removing the element by it`s ID */
    public void remove_by_id(String idStr) {
        try{
            long id = Long.parseLong(idStr);

            if(id == 0){
                System.out.println("ID must be an number greater than 0. Try typing this command again");
                return;
            }
            for (Product product : products) {
                Long intId = product.getId();
                if (intId == id) {
                    products.remove(product);
                    System.out.println("Element was successfully removed.");
                    return;
                }
            }
        }
        catch (NumberFormatException exception){
            System.out.println("ID must be an number. Try typing this command again");
        }

        System.out.println("Element with this ID is not defined. Try again.");
    }

    /** Method for updating the element by it`s ID */
    public void update_by_id(String idStr) {
        try{
            long id = Long.parseLong(idStr);

            if(id <= 0){
                System.out.println("ID must be an number greater than 0. Try typing this command again");
                return;
            }
            System.out.println("updateing product with id: " + id);
            for (Product product : products) {
                Long intId = product.getId();
                if (intId == id) {
                    products.remove(product);
                    var name = inputName();
                    var coord = inputCoordinates();
                    var price = inputPrice();
                    var manufCost = inputManufactureCost();
                    var unit = inputUnitOfMeasure();

                    int yesOrNo = 0;
                    for( ; ; ) {
                        try {
                            System.out.println("enter a number: ");
                            System.out.println("should we add organization? enter the number: 1 - Yes or 2 - No");
                            Scanner scanner = new Scanner(System.in);
                            yesOrNo = scanner.nextInt();
                            if (yesOrNo == 1 || yesOrNo == 2)
                                if(yesOrNo == 2)
                                    System.out.println("organization will not be defined");
                            break;
                        } catch (InputMismatchException e) {
                            System.out.println("enter a number: ");
                        }
                    }
                    var prod = new Product(id, name, coord, price, manufCost,
                            unit, yesOrNo == 1 ? inputOrganization() : null);
                    products.add(prod);
                    Collections.sort(products);
                    System.out.println("Element was updated and sorted successfully.");
                    return;
                }
            }
        }
        catch (NumberFormatException exception){
            System.out.println("ID must be an number. Try typing this command again");
        }

        System.out.println("Element with this ID is not defined. Try again.");
    }

    public String inputName() {
        for ( ; ; ) {
            try {
                System.out.println("Do not enter a very long name, some parts of it may get lost");
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter a name: ");
                String name = scanner.nextLine().trim();
                if (name.equals("")) {
                    System.out.println("This value cannot be empty. Try again");
                    continue;
                }
                return name;
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("This value must be string. Try again.");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully.");
                System.exit(1);
            }
        }
    }

    public OrganizationType inputOrganizationType(){
        for ( ; ; ) {
            try {
                System.out.println("Choose OrganizationType. Enter the number corresponding to the desired option. ");
                System.out.println(" 1 - COMMERCIAL, 2 - GOVERNMENT, 3 - TRUST, 4 - PRIVATE LIMITED COMPANY, 5 - OPEN JOINT STOCK COMPAN");
                Scanner scanner = new Scanner(System.in);
                int nationalityChoice = scanner.nextInt();
                switch (nationalityChoice) {
                    case 1:
                        return OrganizationType.COMMERCIAL;
                    case 2:
                        return OrganizationType.GOVERNMENT;
                    case 3:
                        return OrganizationType.TRUST;
                    case 4:
                        return OrganizationType.PRIVATE_LIMITED_COMPANY;
                    case 5:
                        return OrganizationType.OPEN_JOINT_STOCK_COMPANY;
                    default:
                        break;
                }
                System.out.println("You should enter a number from 1 to 5. Try again. ");
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("This value must be a number. Try again. ");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully. ");
                System.exit(1);
            }
        }
    }

    public void history(){
        System.out.println("last commands:");
        for (var command:
                usedCommandsContainer.usedCommands) {
            System.out.println(command + "\n");
        }
    }

    public UnitOfMeasure inputUnitOfMeasure(){
        for ( ; ; ) {
            try {
                System.out.println("Choose UnitOfMeasure. Enter the number corresponding to the desired option. ");
                System.out.println(" 1 - KILOGRAMS, 2 - METERS, 3 - LITERS, 4 - MILLIGRAMS");
                Scanner scanner = new Scanner(System.in);
                var unitOfMeasure = scanner.nextLine();
                if(unitOfMeasure.isEmpty()){
                    System.out.println("Unit Of Measure is not defined");
                    return null;
                }
                var unitOfMeasureEnumNumber = Integer.valueOf(unitOfMeasure);
                switch (unitOfMeasureEnumNumber) {
                    case 1:
                        return UnitOfMeasure.KILOGRAMS;
                    case 2:
                        return UnitOfMeasure.METERS;
                    case 3:
                        return UnitOfMeasure.LITERS;
                    case 4:
                        return UnitOfMeasure.MILLIGRAMS;
                    default:
                        break;
                }
                System.out.println("You should enter a number from 1 to 4. Try again. ");
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("This value must be a number. Try again. ");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully. ");
                System.exit(1);
            }
        }
    }

    public Organization inputOrganization(){
        var maxId = Long.MIN_VALUE;
        for (var prod: products
        ) {
            if(prod.getManufacturer() !=  null){
                var organization = prod.getManufacturer();
                maxId = Long.max(organization.getId(), maxId);
                }
            }
        System.out.println("adding organization..");
        var org = new Organization(maxId < 0 ? 1 : maxId + 1, inputName(), inputAnnualTurnover(), inputOrganizationType() );
        System.out.println("done with organization..");
        return org;
    }

    public Integer inputAnnualTurnover() {
        for ( ; ; ) {
            try {
                System.out.printf("Enter annual turnover. Note that value can only be from %s to %s: ", 1, Integer.MAX_VALUE);
                Scanner scanner = new Scanner(System.in);
                return scanner.nextInt();
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("This value must be a double-type number. Try again. ");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully. ");
                System.exit(1);
            }
        }
    }

    public void print_unique_unit_of_measure(){
        var set = new HashSet<UnitOfMeasure>();
        for(var prod : products){
            if(prod.getUnitOfMeasure() != null)
                set.add(prod.getUnitOfMeasure());
        }
        for (var unit: set
             ) {
            System.out.println(unit);
        }
    }

    public void filter_greater_than_price(String priceStr){

        try{
            var flag = false;
            var price = Float.parseFloat(priceStr);
            for (var prod : products){
                if(prod.getPrice() > price){
                    System.out.println(prod.toString() + "\n");
                    flag = true;
                }
            }
            if(!flag)
                System.out.println("no such elements found");
        }
        catch (NumberFormatException exception){
            System.out.println("ID must be an number. Try typing this command again");
        }

    }
    /**
     * Method for receiving x-coordinate of location of element
     * @return Double xLocation
     */
    public Double inputXLocation() {
        for ( ; ; ) {
            try {

                System.out.print("Enter X coordinate of location: ");
                Scanner scanner = new Scanner(System.in);
                return scanner.nextDouble();
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("This value must be a double-type number. Try again. ");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully. ");
                System.exit(1);
            }
        }
    }

    /**
     * Method for receiving y-coordinate of element
     * @return float yLocation
     */
    public float inputYLocation() {
        for ( ; ; ) {
            try {
                System.out.print("Enter Y coordinate of location: ");
                Scanner scanner = new Scanner(System.in);
                return scanner.nextFloat();
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("This value must be a float-type number. Try again. ");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully. ");
                System.exit(1);
            }
        }
    }

}
