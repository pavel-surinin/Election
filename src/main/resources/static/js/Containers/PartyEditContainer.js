  var PartyEditContainer = React.createClass({
	  getInitialState: function() {
		    return {
		      countyDetails : [],
		      name : '',
		    };
		  },

		  componentWillMount: function() {
		    var self = this;
		    axios
		    .get('/party/' + this.props.params.id)
		    .then(function(response){
		      self.setState({
		        id : response.data.id,
		        name: response.data.name,
		      });
		    })
		    .catch(function(err){
		      console.error('PartyEditContainer.componentWillMount.axios.get/admin/party/:id', err);
		    });
		  },

		  onHandleChangeName : function(event){
		    this.setState({name: event.target.value});
		  },

		  onHandleUpdate: function(event) {
		    var self = this;
		    axios
		    .put('/admin/party/' + self.state.id,
		      {
		        id : self.state.id,
		        name: self.state.name,})
		    .then(function(response){
		      console.log(response.data);
		      self.context.router.push('/admin/party');
		    })
		    .catch(function(err){
		      console.error('Update failed at PartyEditContainer - ', err);
		    });
		    event.preventDefault();
		  },
		  onHandleCancel:function(){
		    this.context.router.push('/admin/party');
		  },

		  render: function() {
		    return (
		      <PartyEditFormComponent
		      submitButtonName='Išsaugoti'
		      
		      onHandleChangeName={this.onHandleChangeName}

		      onHandleUpdate={this.onHandleUpdate}
		      onHandleCancel={this.onHandleCancel}

		      name={this.state.name}
		      />

		    );
		  }
		});

    PartyEditContainer.contextTypes = {
	  router: React.PropTypes.object.isRequired,
	};
	
	window.PartyEditContainer = PartyEditContainer ;
