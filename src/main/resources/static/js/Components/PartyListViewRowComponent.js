var  PartyListViewRowComponent= React.createClass({
  handleDetailsClick: function(id){
    var self = this;
    return function() {
<<<<<<< HEAD
      self.context.router.push('admin/party/' + id);
=======
      self.context.router.push('/admin/party/' + id);
>>>>>>> 33d72678e7d3758dbf8d8a5ed27014994b4f68c8
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
                &nbsp;
              </td>
            </tr>
    );
  }
});
PartyListViewRowComponent.contextTypes = {
  router: React.PropTypes.object.isRequired,
};
window.PartyListViewRowComponent = PartyListViewRowComponent;
