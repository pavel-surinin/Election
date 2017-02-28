var PartyViewRowComponent = React.createClass({
  handleDetailsClick: function(id){
    var self = this;
    return function() {
      self.context.router.push('party/' + id);
    };
  },
  render: function() {
    return (
      <tr>
        <td className="small">
          {this.props.partyNumber}
        </td>
        <td className="small">
        <a href={'#/party/' + this.props.id}>  {this.props.name} </a>
        </td>
        <td>
          {this.props.members}
        </td>
        <td className="small">
          <button
            onClick={this.handleDetailsClick(this.props.id)}
            id={'details-button-' + this.props.id}
            className='btn btn-info btn-sm fa fa-info'
            ref="info"
            title="Detali partijos Informacija"
            role='button'
            >
          </button>
        </td>
      </tr>
    );
  }
});
PartyViewRowComponent.contextTypes = {
  router: React.PropTypes.object.isRequired,
};
window.PartyViewRowComponent = PartyViewRowComponent;
