package seedu.address.model.group;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.FreeTime;
import seedu.address.model.TimeInterval;
import seedu.address.model.person.Person;

import java.util.List;
import java.util.stream.Stream;

/**
 * Class representing a group
 */
public class Group {
    public static final String MESSAGE_CONSTRAINTS = "Group names should be alphanumeric and must not be black";
    private final ObservableList<Person> listOfGroupMates = FXCollections.observableArrayList();
    private final String groupName;
    private GroupRemark groupRemark;
    private final FreeTime freeTime = new FreeTime();

    /**
     * Name field must be present and not null.
     */
    public Group(String groupName) {
        requireNonNull(groupName);
        this.groupName = groupName;
        this.groupRemark = new GroupRemark("");
    }

    /**
     * Name field must be present and not null.
     */
    public Group(String groupName, GroupRemark groupRemark) {
        requireNonNull(groupName);
        this.groupName = groupName;
        this.groupRemark = groupRemark;
    }


    /**
     * Name field must be present and not null.
     */
    public Group(String groupName, GroupRemark groupRemark, List<Person> listOfGroupMates) {
        requireNonNull(groupName);
        requireNonNull(listOfGroupMates);
        this.groupName = groupName;
        this.groupRemark = groupRemark;
        this.listOfGroupMates.addAll(listOfGroupMates);
    }

    public String getGroupName() {
        return groupName;
    }

    public ObservableList getGroupMates() {
        return listOfGroupMates;
    }

    /**
     * Returns true if both groups have the same name.
     * This defines a weaker notion of equality between two groups.
     */
    public boolean isSameGroup(Group otherGroup) {
        if (otherGroup == this) {
            return true;
        }

        return otherGroup != null
            && this.equals(otherGroup);
    }

    /**
     * Check if same group according to name since groupName is unique
     *
     * @param groupName of interest
     * @return whether group is the same group
     */
    public boolean nameEquals(String groupName) {
        return this.groupName.equals(groupName);
    }

    /**
     * Returns if the name of the group is valid.
     *
     * @param name The name of the group
     * @return The validity of the group name.
     */
    //For now no constraints
    public static boolean isValidGroup(String name) {
        requireNonNull(name);
        return !name.isBlank();
    }

    /**
     * Removes the person from the group.
     * The person must exist in the group.
     */
    public void removePerson(Person toRemove) throws CommandException {
        requireNonNull(toRemove);
        if (!contains(toRemove)) {
            throw new CommandException(
                String.format("%s is not in this group: %s", toRemove.getName().fullName, this.groupName));
        }
        listOfGroupMates.remove(toRemove);
    }

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Person toCheck) {
        requireNonNull(toCheck);
        return listOfGroupMates.stream().anyMatch(toCheck::isSamePerson);
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void addPerson(Person personToAdd) throws CommandException {
        requireNonNull(personToAdd);
        if (this.contains(personToAdd)) {
            throw new CommandException(
                String.format("%s is already in this group: %s", personToAdd.getName().fullName, this.groupName));
        }
        listOfGroupMates.add(personToAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("Group name", groupName)
            .toString();
    }

    public void printGrpMates() {
        this.listOfGroupMates.forEach(x -> System.out.println(x.getName()));
    }

    public ObservableList<Person> getListOfGroupMates() {
        return this.listOfGroupMates;
    }

    @Override
    public boolean equals(Object group) {
        if (group == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(group instanceof Group)) {
            return false;
        }

        Group otherGroup = (Group) group;
        return this.groupName.equals(otherGroup.getGroupName());
    }

    public Stream<Person> grpMatesStream() {
        return listOfGroupMates.stream();
    }

    public GroupRemark getGroupRemark() {
        return this.groupRemark;
    }

    public void setGroupRemark(GroupRemark groupRemark) {
        this.groupRemark = groupRemark;
    }

    public void addFreeTime(ArrayList<TimeInterval> toAddFreeTime) {
        this.freeTime.addTime(toAddFreeTime);
    }

    public FreeTime getFreeTime() {
        return this.freeTime;
    }
}
