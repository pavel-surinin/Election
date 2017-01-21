var CountyCreateContainer = React.createClass({
  getInitialState: function() {
    return {
      name : '',
    };
  },
  onHandleNameChange : function(event){
  this.setState({name : event.target.value});
},
  onHandleSubmit : function(event){
    var self = this;
    event.preventDefault();
    var countyName = {name: this.state.name};
    axios
      .post('/county', countyName)
      .then(function(response){
        console.log(response);
        self.context.router.push('/admin/county');
      })
      .catch(function(err){
        console.error('CountyCreateContainer.onHandleSubmit.axios', err);
      });
  },
  render: function() {
    return (
      <CountyCreateEditFormComponent
       onHandleNameChange={this.onHandleNameChange}
       name={this.state.name}
       onHandleSubmit={this.onHandleSubmit}

       action='Sukurti'
       />
    );
  }
});
CountyCreateContainer.contextTypes = {
  router: React.PropTypes.object.isRequired,
};
window.CountyCreateContainer = CountyCreateContainer ;
