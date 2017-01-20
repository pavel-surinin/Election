var  PartyListViewRowComponent= React.createClass({
  handleDetailsClick: function(id){
    var self = this;
    return function() {
      self.context.router.push('/party/' + id);
    };
  },
  render: function() {
    return (
            <tr>
              <td>
                {this.props.id}
              </td>
              <td>
                {this.props.name}
              </td>
              <td>
                <button onClick={this.handleDetailsClick(this.props.id)} className='btn btn-primary btn-sm' role='button'>Detaliau</button>
              </td>
            </tr>
    );
  }
});
PartyListViewRowComponent.contextTypes = {
  router: React.PropTypes.object.isRequired,
};
window.PartyListViewRowComponent = PartyListViewRowComponent;
