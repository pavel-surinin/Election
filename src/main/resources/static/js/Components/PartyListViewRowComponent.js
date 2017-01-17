var  PartyListViewRowComponent= React.createClass({

  render: function() {
    return (
            <tr>
              <td>
                {this.props.nr}
              </td>
              <td>
                {this.props.partyName}
              </td>
              <td>
                <button type="button" className="btn btn-primary btn-sm">Naujinti</button>
                <button type="button" className="btn btn-danger btn-sm">Trinti</button>
              </td>
            </tr>
    );
  }

});

window.PartyListViewRowComponent = PartyListViewRowComponent;
