package lapr2.framework.company.manager;

import lapr2.framework.homeservices.HomeServices;
import lombok.Getter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Provides the base model interface to the managers of the company, providing access to all its elements.
 *
 * @param <E> the class to be stored in the manager
 * @author flow
 */
public abstract class Manager<E extends Serializable> {

	/**
	 * The path to the binary file.
	 */
	protected final String filePath;

	/**
	 * The collection of elements of the given manager.
	 */
	@Getter
	protected ArrayList<E> elements = new ArrayList<>();

	/**
	 * Constructs a new manager associating the path to its binary file.
	 *
	 * @param filePath the path to the binary file
	 */
	public Manager(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * Loads the information from the binary file.
	 *
	 * @return <code>true</code> if the information was successfully loaded, <code>false</code> otherwise
	 */
	public boolean load() {
		if (filePath == null)
			return false;

		File file = new File(filePath);
		if (!file.exists()) {
			save();
			return true;
		}

		boolean loaded = false;

		try (FileInputStream fileInputStream = new FileInputStream(this.filePath)) {

			loaded = loadList(fileInputStream);

		} catch (IOException e) {
			HomeServices.getInstance().getLoggerManager().getLogger().log(Level.SEVERE, "Could not load a manager", e);
		}

		return loaded;
	}

	/**
	 * Saves the elements of the manager in a binary file.
	 *
	 * @return <code>true</code> if the elements were successfully saved, <code>false</code> otherwise
	 */
	public boolean save() {
		if (filePath == null)
			return false;

		boolean saved = false;

		try (FileOutputStream fileOutputStream = new FileOutputStream(this.filePath); ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
			if (saveList(objectOutputStream)) saved = true;
		} catch (IOException e) {
			HomeServices.getInstance().getLoggerManager().getLogger().log(Level.SEVERE, "Could not save binary file", e);
		}

		return saved;
	}

	/**
	 * Adds an instance to the collection.
	 *
	 * @param e the instance
	 * @return <code>true</code> if the instance was successfully added, <code>false</code> otherwise
	 */
	public boolean add(E e) {
		if (e == null || elements.contains(e)) return false;
		return elements.add(e);
	}

	/**
	 * Verifies if an object is valid.
	 *
	 * @param e the object
	 * @return <code>true</code> if the object is valid, <code>false</code> otherwise
	 */
	public abstract boolean isSecure(E e);

	/**
	 * Verifies if an object is valid globally.
	 *
	 * @param e the object
	 * @return <code>true</code> if the object is valid, <code>false</code> otherwise
	 */
	public abstract boolean isValid(E e);

	/**
	 * Loads the objects in the file input stream object.
	 *
	 * @param fileInputStream file input stream
	 * @return <code>true</code> if the objects were successfully loaded, <code>false</code> otherwise
	 */
	@SuppressWarnings("unchecked")
	private boolean loadList(FileInputStream fileInputStream) {
		try (ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
			Object object = objectInputStream.readObject();

			if (object instanceof List) {
				ArrayList<E> loaded = new ArrayList<>((ArrayList) object);

				for (E e : loaded)
					if (!elements.contains(e))
						elements.add(e);
			}
			return true;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Saves the elements using the object output stream.
	 *
	 * @param objectOutputStream object output stream
	 * @return <code>true</code> if the elements were successfully saved, <code>false</code> otherwise
	 * @throws IOException if a IO exception occurs
	 */
	private boolean saveList(ObjectOutputStream objectOutputStream) throws IOException {
		try {
			objectOutputStream.writeObject(elements);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			objectOutputStream.close();
		}
		return false;
	}
}
