package net.sourceforge.ondex.wsapi.result;

import net.sourceforge.ondex.core.RelationKey;
import net.sourceforge.ondex.wsapi.WebServiceEngine;

/**
 * Key data structure for Relation.
 *
 * @author Christian Brenninkmeijer
 *
 * Based on RelationKey by taubertj, Matthew Pocock
 *
 */
public class WSRelationKey {

	/**
	 * The "from" AbstractConcept id.
	 */
	private int fromId;

	/**
	 * The "to" AbstractConcept id.
	 */
	private int toId;

	/**
	 * The involved relation type id.
	 */
	private String rtId;

	public WSRelationKey() {
    }

        /**
	 * Constructor which fills all fields of this class.
	 *
	 * @param relationKey
	 *            RelationKey to transform
	 */
	public WSRelationKey(RelationKey relationKey) {
        if (relationKey == null) {
            this.fromId = WebServiceEngine.ERROR_ID;
            this.toId = WebServiceEngine.ERROR_ID;
            this.rtId = "";
        } else {
            this.fromId = relationKey.getFromID();
            this.toId = relationKey.getToID();
            this.rtId = relationKey.getRtId();
        }
	}

	/**
	 * Returns the from concept integer id.
	 *
	 * @return Integer
	 */
	public int getFromID() {
		return fromId;
	}

	/**
	 * Returns the from concept integer id.
	 *
	 */
	public void setFromID(int fromId) {
		this.fromId = fromId;
	}

	/**
	 * Returns the to concept integer id.
	 *
	 * @return Integer
	 */
	public int getToID() {
		return toId;
	}

    public void setToId(int toId) {
        this.toId = toId;
    }

    /**
	 * Returns the name of the relation type.
	 *
	 * @return String
	 */
	public String getRtId() {
		return rtId;
	}

    public void setRtId(String rtId) {
        this.rtId = rtId;
    }

}
